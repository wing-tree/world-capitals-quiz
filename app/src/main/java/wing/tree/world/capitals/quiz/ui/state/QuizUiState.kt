package wing.tree.world.capitals.quiz.ui.state

import androidx.compose.runtime.MutableIntState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import wing.tree.world.capitals.quiz.data.model.AnswerReview
import wing.tree.world.capitals.quiz.data.model.History
import wing.tree.world.capitals.quiz.model.Question

sealed interface QuizUiState {
    fun isSummary() = this is Content.Summary

    object Loading : QuizUiState

    sealed interface Content : QuizUiState {
        val key: String

        val count: Int get() = when (this) {
            is InProgress -> questions.count()
            is Summary -> answerReviews.count()
        }

        data class InProgress(
            val questions: ImmutableList<Question>,
            val round: MutableIntState,
        ) : Content {
            override val key: String = Keys.IN_PROGRESS
        }

        data class Summary(
            val answerReviews: ImmutableList<AnswerReview>,
            val favorites: ImmutableSet<String>,
            val history: History,
        ) : Content {
            override val key: String = Keys.SUMMARY

            val score: Int get() = answerReviews.count {
                it.correct
            }
        }

        private object Keys {
            const val IN_PROGRESS = "in_progress"
            const val SUMMARY = "summary"
        }
    }

    sealed interface Action {
        object Back : Action
        object Histories: Action
        object Home : Action
        object Replay : Action

        data class Complete(val inProgress: Content.InProgress) : Action
        data class DoubleBack(val uiState: QuizUiState) : Action
        data class Share(val history: History) : Action
    }
}
