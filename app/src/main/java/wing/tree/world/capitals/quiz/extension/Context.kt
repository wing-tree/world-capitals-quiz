package wing.tree.world.capitals.quiz.extension

import android.content.Context
import android.util.TypedValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import wing.tree.world.capitals.quiz.constant.Preferences.Companion.NAME

val Context.actionBarSize: Int get() = with(theme) {
    val typedValue = TypedValue()

    resolveAttribute(com.google.android.material.R.attr.actionBarSize, typedValue, true)
    TypedValue.complexToDimensionPixelSize(typedValue.data, resources.displayMetrics)
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = NAME)
