package wing.tree.world.capitals.quiz.ui.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import wing.tree.world.capitals.quiz.data.model.History

sealed interface HistoryUiState {
    object Loading : HistoryUiState

    sealed interface Content : HistoryUiState {
        data class Histories(val value: ImmutableList<History>) : Content
        data class Summary(
            val history: History,
            val favorites: ImmutableSet<String>,
        ) : Content
    }

    sealed interface Action {
        object Back : Action

        data class Select(val history: History) : Action
        data class Favorite(val key: String) : Action
        data class Delete(val history: History) : Action
    }
}
