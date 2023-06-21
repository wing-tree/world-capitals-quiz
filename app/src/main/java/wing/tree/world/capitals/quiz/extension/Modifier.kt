package wing.tree.world.capitals.quiz.extension

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import wing.tree.world.capitals.quiz.data.constant.SEVEN
import wing.tree.world.capitals.quiz.data.constant.ZERO
import wing.tree.world.capitals.quiz.data.constant.ZERO_ANGLE
import wing.tree.world.capitals.quiz.data.extension.float
import wing.tree.world.capitals.quiz.data.extension.half
import wing.tree.world.capitals.quiz.data.extension.hundreds
import wing.tree.world.capitals.quiz.data.extension.radians
import wing.tree.world.capitals.quiz.data.extension.square
import wing.tree.world.capitals.quiz.ui.theme.CloudWhite
import wing.tree.world.capitals.quiz.ui.theme.SkyBlue
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

fun Modifier.bounceVertically(
    targetValue: Dp,
    animation: DurationBasedAnimationSpec<Float> = tween(
        durationMillis = SEVEN.hundreds,
        easing = FastOutLinearInEasing,
    ),
): Modifier = composed {
    bounceVertically(
        targetValue = with(LocalDensity.current) {
            targetValue.toPx()
        },
        animation = animation,
    )
}

fun Modifier.bounceVertically(
    targetValue: Float,
    animation: DurationBasedAnimationSpec<Float> = tween(
        durationMillis = SEVEN.hundreds,
        easing = FastOutLinearInEasing,
    ),
): Modifier = composed {
    val value by rememberInfiniteTransition().animateFloat(
        initialValue = ZERO.float,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = animation,
            repeatMode = RepeatMode.Reverse,
        )
    )

    then(this).graphicsLayer {
        translationY = value
    }
}

fun Modifier.gradient(
    colors: ImmutableList<Color> = persistentListOf(SkyBlue, CloudWhite),
    angle: Float = ZERO_ANGLE,
) = then(
    Modifier.drawBehind {
        val width = size.width
        val height = size.height

        val angleRad = angle.radians
        val x = cos(angleRad).float
        val y = sin(angleRad).float

        val radius = sqrt(width.square().plus(height.square())).half
        val offset = center + Offset(x.times(radius), y.times(radius))

        val exactOffset = Offset(
            x = min(offset.x.coerceAtLeast(ZERO.float), width),
            y = height.minus(min(offset.y.coerceAtLeast(ZERO.float), height))
        )

        drawRect(
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(width, height).minus(exactOffset),
                end = exactOffset
            ),
            size = size,
        )
    }
)
