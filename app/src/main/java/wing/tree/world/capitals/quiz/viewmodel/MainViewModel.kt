package wing.tree.world.capitals.quiz.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wing.tree.bruni.billing.BillingService
import com.wing.tree.bruni.billing.BillingService.Companion.purchased
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import wing.tree.world.capitals.quiz.constant.Preferences
import wing.tree.world.capitals.quiz.constant.ProductId
import wing.tree.world.capitals.quiz.constant.products
import wing.tree.world.capitals.quiz.data.extension.isNull

class MainViewModel(private val application: Application) : AndroidViewModel(application) {
    private val ioDispatcher = Dispatchers.IO

    val billingService = BillingService(application, products)

    init {
        viewModelScope.launch(ioDispatcher) {
            with(billingService) {
                for (purchase in purchases) {
                    val value = purchase.orNull() ?: continue

                    if (value.purchased) {
                        processPurchase(value).orNull()?.let {
                            if (ProductId.remove_ads in it.products) {
                                Preferences.AdFreePurchased.put(application, true)
                            }
                        }
                    }
                }
            }
        }

        viewModelScope.launch(ioDispatcher) {
            val firstLaunchedAt = Preferences.FirstLaunchedAt
                .invoke(application)
                .firstOrNull()

            if (firstLaunchedAt.isNull()) {
                Preferences.FirstLaunchedAt
                    .put(
                        application,
                        System.currentTimeMillis(),
                    )
            }
        }
    }
}
