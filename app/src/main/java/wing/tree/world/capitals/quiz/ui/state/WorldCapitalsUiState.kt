package wing.tree.world.capitals.quiz.ui.state

import kotlinx.collections.immutable.ImmutableSet

sealed interface WorldCapitalsUiState {
    object Loading : WorldCapitalsUiState

    data class Content(
        val favorites: ImmutableSet<String>,
    ) : WorldCapitalsUiState
}
