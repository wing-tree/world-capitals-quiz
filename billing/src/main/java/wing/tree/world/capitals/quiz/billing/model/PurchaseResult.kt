package wing.tree.world.capitals.quiz.billing.model

import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase

sealed interface PurchaseResult {
    val purchase: Purchase?

    sealed interface Success : PurchaseResult {
        override val purchase: Purchase

        data class Newly(override val purchase: Purchase) : Success
        data class Pending(override val purchase: Purchase) : Success
        data class Previously(override val purchase: Purchase) : Success
    }

    data class Failure(
        override val purchase: Purchase?,
        val billingResult: BillingResult?
    ) : PurchaseResult
}

inline fun PurchaseResult.onSuccess(function: (PurchaseResult.Success) -> Unit): PurchaseResult {
    if (this is PurchaseResult.Success) {
        function(this)
    }

    return this
}

inline fun PurchaseResult.onFailure(function: (PurchaseResult.Failure) -> Unit): PurchaseResult {
    if (this is PurchaseResult.Failure) {
        function(this)
    }

    return this
}
