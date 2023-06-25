package wing.tree.world.capitals.quiz.data.repository

import android.content.Context
import wing.tree.world.capitals.quiz.data.database.Database
import wing.tree.world.capitals.quiz.data.model.History

class HistoryRepository(context: Context) {
    private val dao = Database.getInstance(context).historyDao

    suspend fun add(history: History){
        dao.insert(history)
    }

    suspend fun delete(history: History) {
        dao.delete(history)
    }

    fun get(primaryKey: Long) = dao.get(primaryKey)

    fun load() = dao.load()
}
