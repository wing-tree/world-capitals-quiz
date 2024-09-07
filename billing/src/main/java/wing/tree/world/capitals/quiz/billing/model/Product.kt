package wing.tree.world.capitals.quiz.billing.model

import com.android.billingclient.api.BillingClient.ProductType

sealed interface Product {
    val id: String
    @ProductType val productType: String

    data class INAPP(
        override val id: String,
        val consumable: Boolean
    ) : Product {
        override val productType: String
            get() = ProductType.INAPP
    }

    data class SUBS(
        override val id: String
    ) : Product {
        override val productType: String
            get() = ProductType.SUBS
    }
}
