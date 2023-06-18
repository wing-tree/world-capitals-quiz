package wing.tree.world.capitals.quiz.ui.compose

import androidx.activity.ComponentActivity
import androidx.compose.runtime.staticCompositionLocalOf
import wing.tree.world.capitals.quiz.noLocalProvidedFor

val LocalActivity = staticCompositionLocalOf<ComponentActivity> {
    noLocalProvidedFor("LocalActivity")
}
