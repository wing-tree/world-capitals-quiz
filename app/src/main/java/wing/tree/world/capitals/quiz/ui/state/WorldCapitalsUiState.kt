package wing.tree.world.capitals.quiz.ui.state

import kotlinx.collections.immutable.ImmutableSet

sealed interface WorldCapitalsUiState {
    object Loading : WorldCapitalsUiState

    data class Content(
        val starred: ImmutableSet<String>,
    ) : WorldCapitalsUiState
}
