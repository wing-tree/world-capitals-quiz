package wing.tree.world.capitals.quiz.ui.state

import kotlinx.collections.immutable.ImmutableSet

sealed interface FavoritesUiState {
    object Loading : FavoritesUiState

    data class Content(
        val favorites: ImmutableSet<String>,
    ) : FavoritesUiState
}
