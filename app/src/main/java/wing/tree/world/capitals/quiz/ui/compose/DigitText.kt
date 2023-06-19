package wing.tree.world.capitals.quiz.ui.compose

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import wing.tree.world.capitals.quiz.data.constant.EMPTY
import wing.tree.world.capitals.quiz.data.constant.ONE
import wing.tree.world.capitals.quiz.data.extension.digitToIntOrDefault
import wing.tree.world.capitals.quiz.data.extension.isNegative

@Composable
fun DigitText(
    digit: Char,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = ONE,
    onTextLayout: (TextLayoutResult) -> Unit = {
        /* no-op */
    },
    style: TextStyle = LocalTextStyle.current
) {
    var digitState by remember {
        mutableIntStateOf(digit.digitToIntOrDefault(ONE.unaryMinus()))
    }

    AnimatedContent(
        targetState = digit.digitToIntOrDefault(ONE.unaryMinus()),
        modifier = modifier,
        transitionSpec = {
            if (digitState < targetState) {
                slideInVertically {
                    it.unaryMinus()
                }.plus(fadeIn()) togetherWith slideOutVertically {
                    it
                }.plus(fadeOut())
            } else if (digitState > targetState) {
                slideInVertically {
                    it
                }.plus(fadeIn()) togetherWith slideOutVertically {
                    it.unaryMinus()
                }.plus(fadeOut())
            } else {
                EnterTransition.None togetherWith ExitTransition.None
            }
        }
    ) {
        Text(
            text = if (it.isNegative) {
                EMPTY
            } else {
                "$it"
            },
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout,
            style = style,
        )

        SideEffect {
            digitState = it
        }
    }
}
