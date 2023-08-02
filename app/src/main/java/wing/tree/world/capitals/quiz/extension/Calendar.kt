package wing.tree.world.capitals.quiz.extension

import android.icu.util.Calendar

val Calendar.julianDay: Int get() = get(Calendar.JULIAN_DAY)
