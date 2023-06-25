package wing.tree.world.capitals.quiz.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import wing.tree.world.capitals.quiz.constant.Preferences
import wing.tree.world.capitals.quiz.data.extension.isNull
import wing.tree.world.capitals.quiz.data.model.History
import wing.tree.world.capitals.quiz.data.repository.HistoryRepository
import wing.tree.world.capitals.quiz.ui.state.HistoryUiState

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val ioDispatcher = Dispatchers.IO
    private val historyRepository = HistoryRepository(application)
    private val histories = historyRepository.load()
    private val favorites = Preferences.Favorites(application)
    private val selectedHistory = MutableStateFlow<History?>(null)

    val uiState = combine(
        histories,
        favorites,
        selectedHistory,
    ) { histories, favorites, selectedHistory ->
        if (selectedHistory.isNull()) {
            HistoryUiState.Content.Histories(
                value = histories.toImmutableList(),
            )
        } else {
            HistoryUiState.Content.Summary(
                history = selectedHistory,
                favorites = favorites.toImmutableSet(),
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HistoryUiState.Loading
    )

    fun select(history: History?) {
        selectedHistory.update {
            history
        }
    }

    fun delete(history: History) {
        viewModelScope.launch(ioDispatcher) {
            historyRepository.delete(history)
        }
    }
}
