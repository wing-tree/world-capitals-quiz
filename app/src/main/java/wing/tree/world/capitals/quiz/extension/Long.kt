package wing.tree.world.capitals.quiz.extension

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.annotation.StringRes
import wing.tree.world.capitals.quiz.R
import wing.tree.world.capitals.quiz.calendar
import wing.tree.world.capitals.quiz.data.constant.TWO
import wing.tree.world.capitals.quiz.data.constant.ZERO
import wing.tree.world.capitals.quiz.data.extension.`is`
import java.util.Locale

private val Long.year: Int get() = Calendar.getInstance()
    .also {
        it.timeInMillis = this
    }.get(Calendar.YEAR)

fun Long.format(context: Context): String {
    val duration = calendar()
        .julianDay
        .minus(calendar(this).julianDay)

    val locale = Locale.getDefault()

    fun getString(@StringRes resId: Int) = context.getString(resId)

    return when {
        duration `is` ZERO -> SimpleDateFormat(getString(R.string.hours_format), locale).format(this)
        duration < TWO -> getString(R.string.yesterday)
        else ->  if (year `is` Calendar.getInstance().get(Calendar.YEAR)) {
            SimpleDateFormat(getString(R.string.months_format), locale).format(this)
        } else {
            SimpleDateFormat(getString(R.string.years_format), locale).format(this)
        }
    }
}
