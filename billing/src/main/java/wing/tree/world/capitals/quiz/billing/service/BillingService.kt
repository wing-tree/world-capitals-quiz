package wing.tree.world.capitals.quiz.billing.service

import android.app.Activity
import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import arrow.core.Either
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClient.ProductType
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams.SubscriptionUpdateParams.ReplacementMode
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.json.JSONObject
import wing.tree.world.capitals.quiz.billing.extension.function.`is`
import wing.tree.world.capitals.quiz.billing.model.Product
import wing.tree.world.capitals.quiz.billing.model.PurchaseResult

class BillingService(
    private val context: Context,
    private val products: List<Product>
) : PurchasesUpdatedListener {
    private lateinit var billingClient: BillingClient
    private lateinit var billingClientStateListener: BillingClientStateListener
    private lateinit var billingFlowLauncher: BillingFlowLauncher
    private lateinit var purchaseProcessor: PurchaseProcessor

    private val ioDispatcher = Dispatchers.IO
    private val coroutineScope = CoroutineScope(SupervisorJob().plus(ioDispatcher))
    private val mutex = Mutex()

    private val _productDetailsList =
        MutableSharedFlow<Either<BillingResult, List<ProductDetails>?>>()
    private val _purchasesResult = MutableSharedFlow<PurchaseResult>()

    val productDetailsList = _productDetailsList.asSharedFlow()
    val purchasesResult = _purchasesResult.asSharedFlow()

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        processPurchases(
            billingResult = billingResult,
            purchases = purchases
        )
    }

    fun endConnection() {
        billingClient.endConnection()
    }

    fun launchBillingFlow(
        activity: Activity,
        productDetails: ProductDetails,
        offerId: String? = null,
        oldPurchaseToken: String? = null,
        @ReplacementMode subscriptionReplacementMode: Int = ReplacementMode.UNKNOWN_REPLACEMENT_MODE
    ): BillingResult {
        return billingFlowLauncher.launchBillingFlow(
            activity = activity,
            productDetails = productDetails,
            offerId = offerId,
            oldPurchaseToken = oldPurchaseToken,
            subscriptionReplacementMode = subscriptionReplacementMode
        )
    }

    fun reconnect() {
        startConnection(billingClientStateListener)
    }

    fun queryProductDetails() {
        val productList = products.map {
            QueryProductDetailsParams.Product
                .newBuilder()
                .setProductId(it.id)
                .setProductType(it.productType)
                .build()
        }

        val builder = QueryProductDetailsParams.newBuilder()
        val queryProductDetailsParams = builder
            .setProductList(productList)
            .build()

        billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
            coroutineScope.launch {
                _productDetailsList.emit(
                    when (billingResult.responseCode) {
                        BillingResponseCode.OK -> Either.Right(productDetailsList)
                        else -> Either.Left(billingResult)
                    }
                )
            }
        }
    }

    fun queryPurchases(@ProductType productType: String) {
        val builder = QueryPurchasesParams.newBuilder()
        val queryPurchasesParams = builder
            .setProductType(productType)
            .build()

        queryPurchases(queryPurchasesParams)
    }

    fun setup(lifecycleOwner: LifecycleOwner) {
        val pendingPurchasesParams = PendingPurchasesParams
            .newBuilder()
            .enableOneTimeProducts()
            .enablePrepaidPlans()
            .build()

        billingClient = BillingClient
            .newBuilder(context)
            .setListener(this)
            .enablePendingPurchases(pendingPurchasesParams)
            .build()

        billingFlowLauncher = BillingFlowLauncher(billingClient)
        purchaseProcessor = PurchaseProcessor(
            billingClient = billingClient,
            products = products
        )

        lifecycleOwner.lifecycle.addObserver(
            object : DefaultLifecycleObserver {
                override fun onCreate(owner: LifecycleOwner) {
                    super.onCreate(owner)

                    billingClientStateListener = object : BillingClientStateListener {
                        override fun onBillingServiceDisconnected() {

                        }

                        override fun onBillingSetupFinished(billingResult: BillingResult) {
                            queryPurchases(ProductType.INAPP)
                            queryPurchases(ProductType.SUBS)
                        }
                    }

                    startConnection(billingClientStateListener)
                }

                override fun onResume(owner: LifecycleOwner) {
                    super.onResume(owner)

                    queryPurchases(ProductType.INAPP)
                    queryPurchases(ProductType.SUBS)
                }

                override fun onDestroy(owner: LifecycleOwner) {
                    endConnection()

                    super.onDestroy(owner)
                }
            }
        )
    }

    fun startConnection(billingClientStateListener: BillingClientStateListener) {
        billingClient.startConnection(billingClientStateListener)
    }

    private fun processPurchases(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        coroutineScope.launch {
            mutex.withLock {
                when (billingResult.responseCode) {
                    BillingResponseCode.OK -> purchases?.forEach { purchase ->
                        _purchasesResult.emit(purchaseProcessor.processPurchase(purchase))
                    }

                    else -> _purchasesResult.emit(PurchaseResult.Failure(null, billingResult))
                }
            }
        }
    }

    private fun queryPurchases(queryPurchasesParams: QueryPurchasesParams) {
        billingClient.queryPurchasesAsync(queryPurchasesParams) { billingResult, purchases ->
            processPurchases(
                billingResult = billingResult,
                purchases = purchases
            )
        }
    }

    companion object {
        val Purchase.jsonObject: JSONObject get() = JSONObject(originalJson)
        val Purchase.isPending: Boolean get() = purchaseState `is` Purchase.PurchaseState.PENDING
        val Purchase.isPurchased: Boolean get() = purchaseState `is` Purchase.PurchaseState.PURCHASED
        val Purchase.isUnspecifiedState: Boolean get() = purchaseState `is` Purchase.PurchaseState.UNSPECIFIED_STATE
    }
}