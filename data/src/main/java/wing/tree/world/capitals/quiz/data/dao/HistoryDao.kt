package wing.tree.world.capitals.quiz.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import wing.tree.world.capitals.quiz.data.model.History

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(history: History)

    @Delete
    suspend fun delete(history: History)

    @Query("SELECT * FROM history WHERE primaryKey = :primaryKey")
    fun get(primaryKey: Long): Flow<History>

    @Query("SELECT * FROM history ORDER BY timestamp DESC")
    fun load(): Flow<List<History>>
}
