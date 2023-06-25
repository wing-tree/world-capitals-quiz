package wing.tree.world.capitals.quiz.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.collections.immutable.ImmutableList
import wing.tree.world.capitals.quiz.data.constant.ZERO
import wing.tree.world.capitals.quiz.data.extension.long

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true)
    val primaryKey: Long = ZERO.long,
    val difficulty: Difficulty,
    val answerReviews: ImmutableList<AnswerReview>,
    val timestamp: Long = System.currentTimeMillis(),
) {
    val score: Int get() = answerReviews.count {
        it.correct
    }
}
