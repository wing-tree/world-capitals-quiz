package wing.tree.world.capitals.quiz.extension

import android.content.Context
import wing.tree.world.capitals.quiz.R
import wing.tree.world.capitals.quiz.data.constant.NEWLINE
import wing.tree.world.capitals.quiz.data.constant.WorldCapitals
import wing.tree.world.capitals.quiz.data.model.History

fun History.toQuiz(context: Context, withAnswers: Boolean): String {
    val map = WorldCapitals.nations.associateBy { nation ->
        nation.key
    }

    return answerReviews.map {
        buildString {
            val correctAnswer = map[it.correctAnswer.key] ?: return@buildString

            append(
                context.getString(
                    R.string.format,
                    context.getString(correctAnswer.country),
                ),
            )

            if (withAnswers) {
                append("$NEWLINE$NEWLINE")
                append(
                    correctAnswer.capitals.joinToString(NEWLINE) {
                        context.getString(it.capital)
                    }
                )
            }
        }
    }.joinToString("$NEWLINE$NEWLINE")
}
