package wing.tree.world.capitals.quiz.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import wing.tree.world.capitals.quiz.constant.Preferences
import wing.tree.world.capitals.quiz.data.constant.THREE
import wing.tree.world.capitals.quiz.data.extension.hundreds
import wing.tree.world.capitals.quiz.data.extension.milliseconds
import wing.tree.world.capitals.quiz.ui.state.FavoritesUiState
import java.util.concurrent.atomic.AtomicBoolean

class FavoritesViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val isLoading = AtomicBoolean(true)

    val uiState = Preferences.Favorites(application).map { favorites ->
        if (isLoading.compareAndSet(true, false)) {
            delay(THREE.hundreds.milliseconds)
        }

        FavoritesUiState.Content(
            favorites = favorites.toImmutableSet(),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = FavoritesUiState.Loading,
    )
}
