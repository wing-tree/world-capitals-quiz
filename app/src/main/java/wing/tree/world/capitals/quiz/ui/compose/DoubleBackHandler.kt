package wing.tree.world.capitals.quiz.ui.compose

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import wing.tree.world.capitals.quiz.data.constant.TWO_SECONDS_IN_MILLISECONDS

@Composable
fun DoubleBackHandler(
    enabled: Boolean = true,
    interval: Long = TWO_SECONDS_IN_MILLISECONDS,
    onBack: () -> Unit,
    onDoubleBack: () -> Unit,
) {
    var onBackPressed by remember(enabled) {
        mutableStateOf(false)
    }

    LaunchedEffect(onBackPressed) {
        if (onBackPressed) {
            delay(interval)

            onBackPressed = false
        }
    }

    BackHandler(enabled) {
        if (onBackPressed) {
            onDoubleBack()
        } else {
            onBack()
        }

        onBackPressed = true
    }
}