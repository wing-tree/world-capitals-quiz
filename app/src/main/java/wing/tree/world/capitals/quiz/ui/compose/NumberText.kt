package wing.tree.world.capitals.quiz.ui.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.max
import wing.tree.world.capitals.quiz.data.constant.SlotId
import wing.tree.world.capitals.quiz.data.constant.TEN
import wing.tree.world.capitals.quiz.data.constant.ZERO

private const val BLANK = ' '

@Composable
fun NumberText(
    number: Int,
    digits: Int,
    modifier: Modifier = Modifier,
) {
    SubcomposeLayout(modifier = modifier) { constraint ->
        var maxWidth: Dp = ZERO.toDp()

        repeat(TEN) {
            val width = subcompose("$it") {
                Text(text = "$it")
            }
                .first()
                .measure(constraint)
                .width

            maxWidth = max(maxWidth, width.toDp())
        }

        val width = maxWidth.times(digits)
        val content = subcompose(SlotId.CONTENT) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Row(modifier = Modifier.width(width)) {
                    val reversed = "$number".reversed()

                    repeat(digits) {
                        val digit = reversed.getOrElse(it) {
                            BLANK
                        }

                        DigitText(
                            digit = digit,
                            modifier = Modifier.width(maxWidth),
                        )
                    }
                }
            }
        }
            .first()
            .measure(constraint)

        layout(width = content.width, height = content.height) {
            content.place(ZERO, ZERO)
        }
    }
}
