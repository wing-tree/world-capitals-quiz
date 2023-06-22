package wing.tree.world.capitals.quiz.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wing.tree.bruni.billing.BillingService.Companion.purchased
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import wing.tree.world.capitals.quiz.BillingServiceProvider
import wing.tree.world.capitals.quiz.constant.Preferences
import wing.tree.world.capitals.quiz.constant.ProductId.ad_free
import wing.tree.world.capitals.quiz.constant.products
import wing.tree.world.capitals.quiz.model.InAppProduct
import wing.tree.world.capitals.quiz.ui.state.StoreUiState

class StoreViewModel(private val application: Application) : AndroidViewModel(application) {
    private val ioDispatcher = Dispatchers.IO

    val billingService = BillingServiceProvider.getOrCreate(
        context = application,
        products = products,
    )

    val uiState = billingService.productDetailsList.map { productDetailsList ->
        val inAppProducts = productDetailsList?.map {
            InAppProduct(
                productDetails = it,
            )
        } ?: emptyList()

        StoreUiState.Content(inAppProducts.toImmutableList())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = StoreUiState.Loading,
    )

    init {
        viewModelScope.launch(ioDispatcher) {
            billingService.queryProductDetails()
        }

        viewModelScope.launch {
            with(billingService) {
                for (purchase in purchases) {
                    val value = purchase.orNull() ?: continue

                    if (value.purchased) {
                        processPurchase(value).orNull()?.let {
                            if (ad_free in it.products) {
                                Preferences.AdFreePurchased.put(
                                    context = application,
                                    value = true,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
