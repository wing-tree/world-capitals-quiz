package wing.tree.world.capitals.quiz

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewManagerFactory
import timber.log.Timber

object Review {
    fun launchReviewFlow(activity: Activity) {
        val reviewManager = ReviewManagerFactory.create(activity)
        val task = reviewManager.requestReviewFlow()

        task.addOnCompleteListener {
            if (it.isSuccessful) {
                val reviewInfo = it.result

                reviewInfo.describeContents()
                reviewManager.launchReviewFlow(activity, reviewInfo).apply {
                    addOnCompleteListener {
                        with(activity) {
                            val text = getString(R.string.thank_you_for_your_valuable_review)

                            Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                val exception = it.exception

                if (exception is ReviewException) {
                    Timber.e(exception)
                }

                goToGooglePlay(activity)
            }
        }
    }

    private fun goToGooglePlay(context: Context) {
        try {
            context.startActivity(
                Intent (
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=${context.packageName}"))
            )
        } catch (e: ActivityNotFoundException) {
            context.startActivity(
                Intent (
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")
                )
            )
        }
    }
}
