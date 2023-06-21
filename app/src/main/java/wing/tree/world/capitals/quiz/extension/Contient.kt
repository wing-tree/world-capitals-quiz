package wing.tree.world.capitals.quiz.extension

import androidx.annotation.StringRes
import wing.tree.world.capitals.quiz.R
import wing.tree.world.capitals.quiz.data.model.Continent

@get:StringRes
val Continent.stringRes: Int
    get() = when (this) {
        Continent.AMERICA -> R.string.america
        Continent.ASIA -> R.string.asia
        Continent.EUROPE -> R.string.europe
        Continent.OCEANIA -> R.string.oceania
        else -> R.string.africa
    }
