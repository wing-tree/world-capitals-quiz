package wing.tree.world.capitals.quiz.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import wing.tree.world.capitals.quiz.BillingServiceProvider
import wing.tree.world.capitals.quiz.billing.model.onSuccess
import wing.tree.world.capitals.quiz.constant.Preferences
import wing.tree.world.capitals.quiz.constant.ProductId
import wing.tree.world.capitals.quiz.constant.products
import wing.tree.world.capitals.quiz.data.extension.isNull

class MainViewModel(private val application: Application) : AndroidViewModel(application) {
    private val ioDispatcher = Dispatchers.IO

    val billingService = BillingServiceProvider.getOrCreate(application, products)

    init {
        viewModelScope.launch(ioDispatcher) {
            with(billingService) {
                purchasesResult.collect { purchaseResult ->
                    purchaseResult.onSuccess {
                        if (ProductId.ad_free in it.purchase.products) {
                            Preferences.AdFreePurchased.put(application, true)
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
