package wing.tree.world.capitals.quiz.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import com.android.billingclient.api.ProductDetails

@Stable
data class InAppProduct(
    val productDetails: ProductDetails,
    @DrawableRes
    val imageResource: Int? = null
) {
    val key = productDetails.productId
}
