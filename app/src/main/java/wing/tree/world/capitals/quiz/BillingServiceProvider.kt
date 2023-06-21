package wing.tree.world.capitals.quiz

import android.content.Context
import com.wing.tree.bruni.billing.BillingService
import com.wing.tree.bruni.billing.model.Product

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
