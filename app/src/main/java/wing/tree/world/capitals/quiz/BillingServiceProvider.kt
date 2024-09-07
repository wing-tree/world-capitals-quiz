package wing.tree.world.capitals.quiz

import android.content.Context
import wing.tree.world.capitals.quiz.billing.model.Product
import wing.tree.world.capitals.quiz.billing.service.BillingService

object BillingServiceProvider {
    private var billingService: BillingService? = null

    fun getOrCreate(context: Context, products: List<Product>): BillingService {
        return billingService ?: BillingService(
            context = context,
            products = products,
        ).also {
            billingService = it
        }
    }
}
