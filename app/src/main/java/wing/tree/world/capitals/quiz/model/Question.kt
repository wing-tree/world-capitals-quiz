package wing.tree.world.capitals.quiz.model

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.collections.immutable.ImmutableList
import wing.tree.world.capitals.quiz.data.model.Capital

data class Question(
    val answer: MutableState<Int?> = mutableStateOf(null),
    val correctAnswer: Int,
    val options: ImmutableList<Option>,
    val round: Int,
) {
    data class Option(
        val capital: Capital,
        @StringRes
        val country: Int,
    )
}
