package wing.tree.world.capitals.quiz.ui.state

import kotlinx.collections.immutable.ImmutableList
import wing.tree.world.capitals.quiz.model.InAppProduct

sealed interface StoreUiState {
    object Loading : StoreUiState

    data class Content(val inAppProducts: ImmutableList<InAppProduct>) : StoreUiState {
        fun isNotEmpty() = inAppProducts.isNotEmpty()
    }
}
