package wing.tree.world.capitals.quiz.billing.extension.function

import com.android.billingclient.api.Purchase
import wing.tree.world.capitals.quiz.billing.model.Product

operator fun List<Product>.get(id: String): Product? = find { it.id == id }
operator fun List<Product>.get(purchase: Purchase): Product? = find {
    purchase.products.contains(it.id)
}

fun List<Product>.consumable() = filterIsINAPP().filter(Product.INAPP::consumable)
fun List<Product>.contains(purchase: Purchase) = map(Product::id).containsAll(purchase.products)
fun List<Product>.filterIsINAPP() = filterIsInstance<Product.INAPP>()
