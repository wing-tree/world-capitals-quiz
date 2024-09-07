package wing.tree.world.capitals.quiz.billing.service

import android.app.Activity
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams
import com.android.billingclient.api.BillingFlowParams.SubscriptionUpdateParams
import com.android.billingclient.api.BillingFlowParams.SubscriptionUpdateParams.ReplacementMode
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import wing.tree.world.capitals.quiz.billing.extension.function.`is`

class BillingFlowLauncher(private val billingClient: BillingClient) {
    private fun billingFlowParams(
        productDetails: ProductDetails,
        offerId: String? = null,
        oldPurchaseToken: String? = null,
        @ReplacementMode subscriptionReplacementMode: Int = ReplacementMode.UNKNOWN_REPLACEMENT_MODE
    ): BillingFlowParams {
        return when (productDetails.productType) {
            BillingClient.ProductType.INAPP -> {
                val productDetailsParams = ProductDetailsParams
                    .newBuilder()
                    .setProductDetails(productDetails)
                    .build()

                val productDetailsParamsList = listOf(productDetailsParams)

                BillingFlowParams
                    .newBuilder()
                    .setProductDetailsParamsList(productDetailsParamsList)
                    .build()
            }

            BillingClient.ProductType.SUBS -> {
                val subscriptionOfferDetails =
                    productDetails.subscriptionOfferDetails ?: emptyList()
                val offerToken = with(subscriptionOfferDetails) {
                    find {
                        it.offerId `is` offerId
                    }
                        ?: firstOrNull()
                }
                    ?.offerToken

                val productDetailsParamsList = listOf(
                    ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails)
                        .setOfferToken(requireNotNull(offerToken))
                        .build()
                )

                val subscriptionUpdateParams = oldPurchaseToken?.let {
                    SubscriptionUpdateParams
                        .newBuilder()
                        .setOldPurchaseToken(oldPurchaseToken)
                        .setSubscriptionReplacementMode(subscriptionReplacementMode)
                        .build()
                }

                BillingFlowParams
                    .newBuilder()
                    .setProductDetailsParamsList(productDetailsParamsList)
                    .run {
                        subscriptionUpdateParams?.let(::setSubscriptionUpdateParams) ?: this
                    }
                    .build()
            }

            else -> throw IllegalArgumentException(productDetails.productType)
        }
    }

    fun launchBillingFlow(
        activity: Activity,
        productDetails: ProductDetails,
        offerId: String? = null,
        oldPurchaseToken: String? = null,
        @ReplacementMode subscriptionReplacementMode: Int = ReplacementMode.UNKNOWN_REPLACEMENT_MODE
    ): BillingResult {
        val billingFlowParams = billingFlowParams(
            productDetails = productDetails,
            offerId = offerId,
            oldPurchaseToken = oldPurchaseToken,
            subscriptionReplacementMode = subscriptionReplacementMode
        )

        return billingClient.launchBillingFlow(activity, billingFlowParams)
    }
}
