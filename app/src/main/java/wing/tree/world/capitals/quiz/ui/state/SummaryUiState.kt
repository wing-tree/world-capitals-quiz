package wing.tree.world.capitals.quiz.ui.state

import kotlinx.collections.immutable.ImmutableSet
import wing.tree.world.capitals.quiz.data.model.History

sealed interface SummaryUiState {
    object Loading : SummaryUiState

    data class Content(
        val history: History,
        val favorites: ImmutableSet<String>,
    ) : SummaryUiState
}
