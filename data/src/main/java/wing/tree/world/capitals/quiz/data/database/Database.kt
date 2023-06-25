package wing.tree.world.capitals.quiz.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import wing.tree.world.capitals.quiz.data.dao.HistoryDao
import wing.tree.world.capitals.quiz.data.model.History
import wing.tree.world.capitals.quiz.data.typeconverters.TypeConverters

private const val VERSION = 1

@androidx.room.Database(
    entities = [History::class],
    exportSchema = false,
    version = VERSION,
)
@androidx.room.TypeConverters(TypeConverters::class)
abstract class Database: RoomDatabase() {
    abstract val historyDao: HistoryDao

    companion object {
        private const val NAME = "database"

        @Volatile
        private var instance: Database? = null

        fun getInstance(context: Context): Database {
            synchronized(this) {
                return instance ?: run {
                    Room.databaseBuilder(
                        context.applicationContext,
                        Database::class.java,
                        "$NAME:$VERSION"
                    )
                        .build()
                        .also {
                            instance = it
                        }
                }
            }
        }
    }
}
