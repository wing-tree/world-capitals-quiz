package wing.tree.world.capitals.quiz.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import wing.tree.world.capitals.quiz.data.model.Difficulty
import wing.tree.world.capitals.quiz.viewmodel.QuizViewModel

class QuizViewModelFactory(
    private val application: Application,
    private val difficulty: Difficulty,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return QuizViewModel(application, difficulty) as T
    }
}
