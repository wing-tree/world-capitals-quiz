package wing.tree.world.capitals.quiz.data.model

import androidx.annotation.StringRes

data class Option(
    val capital: Capital,
    @StringRes
    val country: Int,
    val key: String,
)
