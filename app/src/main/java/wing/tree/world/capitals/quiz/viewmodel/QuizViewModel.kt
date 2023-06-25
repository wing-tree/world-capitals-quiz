package wing.tree.world.capitals.quiz.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
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
import wing.tree.world.capitals.quiz.data.model.AnswerReview
import wing.tree.world.capitals.quiz.data.model.Difficulty
import wing.tree.world.capitals.quiz.data.model.History
import wing.tree.world.capitals.quiz.data.repository.HistoryRepository
import wing.tree.world.capitals.quiz.model.Question
import wing.tree.world.capitals.quiz.ui.state.QuizUiState
import wing.tree.world.capitals.quiz.ui.state.QuizUiState.Content

class QuizViewModel(
    application: Application,
    private val difficulty: Difficulty,
) : AndroidViewModel(application) {
    private val historyRepository = HistoryRepository(application)
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

                Content.InProgress(
                    round = mutableIntStateOf(ZERO),
                    questions = questions.await()
                )
            }
        }
    }

    init {
        play()
    }

    fun complete(inProgress: Content.InProgress) {
        viewModelScope.launch(ioDispatcher) {
            val favorites = Preferences.Favorites(getApplication())
                .firstOrNull()
                ?: emptySet()

            _uiState.update {
                val answerReviews = inProgress.questions.mapToAnswerReviews()

                val history = History(
                    difficulty = difficulty,
                    answerReviews = answerReviews,
                ).also {
                    historyRepository.add(it)
                }

                Content.Summary(
                    answerReviews = answerReviews,
                    favorites = favorites.toImmutableSet(),
                    history = history,
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

    private fun List<Question>.mapToAnswerReviews() = map { question ->
        with(question) {
            AnswerReview(
                answer = options[answer.value ?: ZERO],
                correctAnswer = options[correctAnswer],
                round = round
            )
        }
    }.toImmutableList()
}
