package wing.tree.world.capitals.quiz.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.android.billingclient.api.BillingClient
import wing.tree.world.capitals.quiz.R
import wing.tree.world.capitals.quiz.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val lifecycleOwner: LifecycleOwner get() = this
    private val viewModel by viewModels<MainViewModel> {
        ViewModelProvider.AndroidViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        with(viewModel.billingService) {
            setup(lifecycleOwner) {
                if (it.responseCode == BillingClient.BillingResponseCode.OK) {
                    queryPurchases(BillingClient.ProductType.INAPP)
                }
            }
        }
    }
}
