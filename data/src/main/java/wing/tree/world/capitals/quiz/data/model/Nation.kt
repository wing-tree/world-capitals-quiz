package wing.tree.world.capitals.quiz.data.model

import android.content.Context
import androidx.annotation.StringRes

data class Nation(
    val capitals: List<Capital>,
    @StringRes
    val country: Int,
    val key: String,
) {
    companion object {
        fun comparator(context: Context) = Comparator<Nation> { o1, o2 ->
            with(context) {
                compareValues(
                    getString(o1.country),
                    getString(o2.country),
                )
            }
        }
    }
}
