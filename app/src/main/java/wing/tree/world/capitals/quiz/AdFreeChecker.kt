package wing.tree.world.capitals.quiz

import android.content.Context
import wing.tree.world.capitals.quiz.constant.Preferences.AdFreePurchased
import wing.tree.world.capitals.quiz.constant.Preferences.FirstLaunchedAt
import wing.tree.world.capitals.quiz.data.constant.AD_FREE_GRACE_PERIOD_IN_HOURS
import java.util.concurrent.TimeUnit

class AdFreeChecker {
    suspend fun check(context: Context): Boolean {
        val adFreePurchased = AdFreePurchased.firstOrDefault(context, false)
        val firstLaunchedAt = FirstLaunchedAt.firstOrDefault(context, Long.MAX_VALUE)

        return if (adFreePurchased) {
            true
        } else {
            val currentTimeMillis = System.currentTimeMillis()
            val duration = currentTimeMillis.minus(firstLaunchedAt)

            TimeUnit.MILLISECONDS.toHours(duration) < AD_FREE_GRACE_PERIOD_IN_HOURS
        }
    }
}
