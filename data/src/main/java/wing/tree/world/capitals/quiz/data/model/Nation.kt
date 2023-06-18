package wing.tree.world.capitals.quiz.data.model

import androidx.annotation.StringRes

data class Nation(
    val capitals: List<Capital>,
    @StringRes
    val country: Int,
    val key: String,
)
