package wing.tree.world.capitals.quiz

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import wing.tree.world.capitals.quiz.data.constant.THREE
import wing.tree.world.capitals.quiz.data.constant.WorldCapitals
import wing.tree.world.capitals.quiz.data.extension.not
import wing.tree.world.capitals.quiz.data.model.Difficulty
import wing.tree.world.capitals.quiz.model.Question

class QuestionGenerator {
    fun generate(difficulty: Difficulty): ImmutableList<Question> {
        val worldCapitals = buildList {
            addAll(WorldCapitals.Africa.nations)
            addAll(WorldCapitals.America.nations)
            addAll(WorldCapitals.Asia.nations)
            addAll(WorldCapitals.Europe.nations)
            addAll(WorldCapitals.Oceania.nations)
        }
            .map {
                Question.Option(
                    capital = it.capitals.random(),
                    country = it.country,
                )
            }

        val correctAnswers = worldCapitals
            .shuffled()
            .take(difficulty.count)

        val questions = mutableListOf<Question>()

        correctAnswers.forEachIndexed { index, correctAnswer ->
            val options = worldCapitals.filter { nation ->
                correctAnswer not nation
            }
                .shuffled()
                .take(OPTION_COUNT)
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

    companion object {
        private const val OPTION_COUNT = THREE
    }
}
