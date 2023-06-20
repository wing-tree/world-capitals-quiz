package wing.tree.world.capitals.quiz

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import wing.tree.world.capitals.quiz.data.constant.FOUR
import wing.tree.world.capitals.quiz.data.constant.WorldCapitals
import wing.tree.world.capitals.quiz.data.extension.not
import wing.tree.world.capitals.quiz.data.model.Difficulty
import wing.tree.world.capitals.quiz.data.model.Nation
import wing.tree.world.capitals.quiz.model.Question

private typealias Answer = Question.Option

class QuestionGenerator {
    fun generate(difficulty: Difficulty): ImmutableList<Question> {
        val answers = WorldCapitals.nations.toAnswers()

        val correctAnswers = answers
            .shuffled()
            .take(difficulty.count)

        val questions = mutableListOf<Question>()

        correctAnswers.forEachIndexed { index, correctAnswer ->
            val options = answers.filter { answer ->
                answer not correctAnswer
            }
                .shuffled()
                .take(OPTION_COUNT.dec())
                .toMutableList()

            options.apply {
                add(correctAnswer)
                shuffle()
            }

            val question = Question(
                options = options.toImmutableList(),
                round = index,
                correctAnswer = options.indexOf(correctAnswer),
            )

            questions.add(index, question)
        }

        return questions.toImmutableList()
    }

    private fun List<Nation>.toAnswers(): List<Question.Option> = map {
        Answer(
            capital = it.capitals.random(),
            country = it.country,
        )
    }

    companion object {
        private const val OPTION_COUNT = FOUR
    }
}
