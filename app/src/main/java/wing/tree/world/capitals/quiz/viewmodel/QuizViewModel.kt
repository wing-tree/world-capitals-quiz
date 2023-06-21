package wing.tree.world.capitals.quiz.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import wing.tree.world.capitals.quiz.QuestionGenerator
import wing.tree.world.capitals.quiz.constant.Preferences
import wing.tree.world.capitals.quiz.data.constant.THREE
import wing.tree.world.capitals.quiz.data.constant.ZERO
import wing.tree.world.capitals.quiz.data.extension.hundreds
import wing.tree.world.capitals.quiz.data.extension.milliseconds
import wing.tree.world.capitals.quiz.data.model.Difficulty
import wing.tree.world.capitals.quiz.ui.state.QuizUiState

class QuizViewModel(
    application: Application,
    private val difficulty: Difficulty,
) : AndroidViewModel(application) {
    private val ioDispatcher = Dispatchers.IO
    private val questionGenerator = QuestionGenerator()
    private val _uiState: MutableStateFlow<QuizUiState> = MutableStateFlow(QuizUiState.Loading)
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private fun play() {
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.update {
                val questions = async {
                    questionGenerator.generate(difficulty)
                }

                delay(THREE.hundreds.milliseconds)

                QuizUiState.InProgress(
                    round = mutableIntStateOf(ZERO),
                    questions = questions.await()
                )
            }
        }
    }

    init {
        play()
    }

    fun complete() {
        viewModelScope.launch(ioDispatcher) {
            val favorites = Preferences.Favorites(getApplication())
                .firstOrNull()
                ?: emptySet()

            _uiState.update {
                QuizUiState.Summary(
                    questions = it.questions,
                    favorites = favorites.toImmutableSet(),
                )
            }
        }
    }

    fun replay() {
        viewModelScope.launch {
            _uiState.update {
                QuizUiState.Loading
            }

            play()
        }
    }
}
