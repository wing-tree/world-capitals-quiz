package wing.tree.world.capitals.quiz

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class InterstitialAdLoader {
    private var interstitialAd: InterstitialAd? = null

    fun clear() {
        interstitialAd = null
    }

    fun load(
        context: Context,
        loadCallback: InterstitialAdLoadCallback? = null,
    ) {
        val adRequest = AdRequest.Builder().build()
        val adUnitId = context.getString(
            if (BuildConfig.DEBUG) {
                R.string.sample_interstitial_ad_unit_id
            } else {
                R.string.interstitial_ad_unit_id
            }
        )

        fun setInterstitialAd(interstitialAd: InterstitialAd) {
            this.interstitialAd = interstitialAd
        }

        InterstitialAd.load(
            context,
            adUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    clear()
                    loadCallback?.onAdFailedToLoad(adError)
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    setInterstitialAd(interstitialAd)
                    loadCallback?.onAdLoaded(interstitialAd)
                }
            }
        )
    }

    fun show(
        activity: Activity,
        fullScreenContentCallback: FullScreenContentCallback? = null,
    ) {
        interstitialAd?.let {
            it.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    fullScreenContentCallback?.onAdClicked()
                }

                override fun onAdDismissedFullScreenContent() {
                    clear()
                    fullScreenContentCallback?.onAdDismissedFullScreenContent()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    clear()
                    fullScreenContentCallback?.onAdFailedToShowFullScreenContent(adError)
                }

                override fun onAdImpression() {
                    fullScreenContentCallback?.onAdImpression()
                }

                override fun onAdShowedFullScreenContent() {
                    fullScreenContentCallback?.onAdShowedFullScreenContent()
                }
            }

            it.show(activity)
        }
    }
}
