package wing.tree.world.capitals.quiz.constant

import android.content.Context
import androidx.datastore.preferences.core.Preferences.Key
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import wing.tree.world.capitals.quiz.data.extension.toggle
import wing.tree.world.capitals.quiz.extension.dataStore

sealed interface Preferences : Function1<Context, Flow<*>> {
    val name: String
    val key: Key<*>

    object Starred : Preferences {
        override val name = "starred"
        override val key: Key<Set<String>> = stringSetPreferencesKey(name)

        override fun invoke(context: Context): Flow<Set<String>> = context
            .dataStore
            .data
            .map { preferences ->
                preferences[key] ?: emptySet()
            }

        suspend fun toggle(context: Context, value: String) {
            context.dataStore.edit { mutablePreferences ->
                mutablePreferences[key] = buildSet {
                    mutablePreferences[key]?.let {
                        addAll(it)
                    }

                    toggle(value)
                }
            }
        }
    }

    companion object {
        const val NAME = "preferences"
    }
}
