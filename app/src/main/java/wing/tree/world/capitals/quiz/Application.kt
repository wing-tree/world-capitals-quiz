package wing.tree.world.capitals.quiz

import android.app.Application
import com.google.android.gms.ads.MobileAds
import timber.log.Timber

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
        Timber.plant(Timber.DebugTree())
    }
}
