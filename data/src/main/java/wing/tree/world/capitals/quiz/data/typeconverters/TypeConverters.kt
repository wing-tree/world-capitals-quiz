package wing.tree.world.capitals.quiz.data.typeconverters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import wing.tree.world.capitals.quiz.data.model.AnswerReview
import wing.tree.world.capitals.quiz.data.model.Difficulty

class TypeConverters {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @TypeConverter
    fun fromDifficulty(difficulty: Difficulty): String {
        return difficulty.name
    }

    @TypeConverter
    fun toDifficulty(value: String): Difficulty {
        return Difficulty.valueOf(value)
    }

    @TypeConverter
    fun fromAnswerReviews(questions: List<AnswerReview>): String {
        val parameterizedType = Types.newParameterizedType(
            List::class.java,
            AnswerReview::class.java,
        )

        val jsonAdapter = moshi.adapter<List<AnswerReview>>(parameterizedType)

        return jsonAdapter.toJson(questions)
    }

    @TypeConverter
    fun toAnswerReviews(value: String): ImmutableList<AnswerReview> {
        val parameterizedType = Types.newParameterizedType(
            List::class.java,
            AnswerReview::class.java,
        )

        val jsonAdapter = moshi.adapter<List<AnswerReview>>(parameterizedType)

        return jsonAdapter.fromJson(value)?.toImmutableList() ?: persistentListOf()
    }
}
