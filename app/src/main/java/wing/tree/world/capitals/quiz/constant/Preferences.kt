package wing.tree.world.capitals.quiz.constant

import android.content.Context
import androidx.datastore.preferences.core.Preferences.Key
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import wing.tree.world.capitals.quiz.data.extension.toggle
import wing.tree.world.capitals.quiz.extension.dataStore

sealed interface Preferences : (Context) -> Flow<*> {
    val name: String
    val key: Key<*>

    object AdFreePurchased : Preferences {
        override val name: String = "ad_free_purchased"
        override val key: Key<Boolean> = booleanPreferencesKey(name)

        override fun invoke(context: Context): Flow<Boolean> = context
            .dataStore
            .data
            .map { preferences ->
                preferences[key] ?: false
            }

        suspend fun firstOrDefault(context: Context, defaultValue: Boolean) : Boolean {
            return invoke(context).firstOrNull() ?: defaultValue
        }

        suspend fun put(context: Context, value: Boolean) {
            context.dataStore.edit { mutablePreferences ->
                mutablePreferences[key] = value
            }
        }
    }

    object FirstLaunchedAt : Preferences {
        override val name: String = "first_launched_at"
        override val key: Key<Long> = longPreferencesKey(name)

        override fun invoke(context: Context): Flow<Long?> = context
            .dataStore
            .data
            .map { preferences ->
                preferences[key]
            }

        suspend fun firstOrDefault(context: Context, defaultValue: Long) : Long {
            return invoke(context).firstOrNull() ?: defaultValue
        }

        suspend fun put(context: Context, value: Long) {
            context.dataStore.edit { mutablePreferences ->
                mutablePreferences[key] = value
            }
        }
    }

    object Favorites : Preferences {
        override val name = "favorites"
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
