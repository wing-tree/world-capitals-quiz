package wing.tree.world.capitals.quiz.ui.state

import androidx.compose.runtime.MutableIntState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import wing.tree.world.capitals.quiz.model.Question

sealed interface QuizUiState {
    val questions: ImmutableList<Question>

    object Loading : QuizUiState {
        override val questions: ImmutableList<Question> = persistentListOf()
    }

    data class InProgress(
        override val questions: ImmutableList<Question>,
        val round: MutableIntState,
    ) : QuizUiState

    data class Summary(
        override val questions: ImmutableList<Question>,
        val favorites: ImmutableSet<String>,
    ) : QuizUiState

    sealed interface Action {
        object Back : Action
        object Complete : Action
        object Home : Action
        object Replay : Action

        data class DoubleBack(val uiState: QuizUiState) : Action
    }
}
