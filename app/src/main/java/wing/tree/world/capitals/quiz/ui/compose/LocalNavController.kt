package wing.tree.world.capitals.quiz.ui.compose

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import wing.tree.world.capitals.quiz.noLocalProvidedFor

val LocalNavController = staticCompositionLocalOf<NavController> {
    noLocalProvidedFor("LocalNavController")
}
