package wing.tree.world.capitals.quiz.data.model

import wing.tree.world.capitals.quiz.data.extension.`is`

data class AnswerReview(
    val answer: Option,
    val correctAnswer: Option,
    val round: Int,
) {
    val correct: Boolean get() = answer.key `is` correctAnswer.key
}
