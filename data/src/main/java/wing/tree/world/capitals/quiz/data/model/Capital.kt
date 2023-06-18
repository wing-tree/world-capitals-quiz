package wing.tree.world.capitals.quiz.data.model

import androidx.annotation.StringRes

data class Capital(
    @StringRes
    val capital: Int,
    val role: Role? = null,
)
