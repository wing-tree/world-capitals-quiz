package wing.tree.world.capitals.quiz.billing.service

import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.acknowledgePurchase
import com.android.billingclient.api.consumePurchase
import wing.tree.world.capitals.quiz.billing.extension.function.consumable
import wing.tree.world.capitals.quiz.billing.model.Product
import wing.tree.world.capitals.quiz.billing.model.PurchaseResult
import wing.tree.world.capitals.quiz.billing.service.BillingService.Companion.isPending
import wing.tree.world.capitals.quiz.billing.service.BillingService.Companion.isPurchased

class PurchaseProcessor(
    private val billingClient: BillingClient,
    private val products: List<Product>
) {
    suspend fun processPurchase(purchase: Purchase): PurchaseResult {
        return when {
            purchase.isAcknowledged -> PurchaseResult.Success.Previously(purchase)
            purchase.isPending -> PurchaseResult.Success.Pending(purchase)
            purchase.isPurchased -> when {
                consumable(purchase) -> consumePurchase(purchase)
                else -> acknowledgePurchase(purchase)
            }

            else -> PurchaseResult.Failure(purchase, null)
        }
    }

    private fun consumable(purchase: Purchase): Boolean {
        return products.consumable().map(Product::id).containsAll(purchase.products)
    }

    private suspend fun acknowledgePurchase(purchase: Purchase): PurchaseResult {
        val acknowledgePurchaseParams = AcknowledgePurchaseParams
            .newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        val billingResult = billingClient.acknowledgePurchase(acknowledgePurchaseParams)

        return when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> PurchaseResult.Success.Newly(purchase)
            else -> PurchaseResult.Failure(
                purchase = purchase,
                billingResult = billingResult
            )
        }
    }

    private suspend fun consumePurchase(purchase: Purchase): PurchaseResult {
        val consumeParams = ConsumeParams
            .newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        val consumeResult = billingClient.consumePurchase(consumeParams)
        val billingResult = consumeResult.billingResult

        return when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> PurchaseResult.Success.Newly(purchase)
            else -> PurchaseResult.Failure(
                purchase = purchase,
                billingResult = billingResult
            )
        }
    }
}
