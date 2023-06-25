package wing.tree.world.capitals.quiz.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.collections.immutable.ImmutableList
import wing.tree.world.capitals.quiz.data.model.Option

data class Question(
    val answer: MutableState<Int?> = mutableStateOf(null),
    val correctAnswer: Int,
    val options: ImmutableList<Option>,
    val round: Int,
)
