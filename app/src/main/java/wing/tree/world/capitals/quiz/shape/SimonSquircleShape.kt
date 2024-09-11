package wing.tree.world.capitals.quiz.shape

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path

class SimonSquircleShape {

}

fun squircleShape(superRadius: Float = 5.0f): GenericShape {
    return GenericShape { size, _ ->
        val rect = Rect(0f, 0f, size.width, size.height)
        addPath(createSquirclePath(rect, superRadius))
    }
}

fun createSquirclePath(rect: Rect, superRadius: Float): Path = Path().apply {
    val cX = rect.center.x
    val cY = rect.center.y

    val dx = cX * (1.0f / superRadius)
    val dy = cY * (1.0f / superRadius)

    moveTo(cX, 0f)

    // Top-right curve
    relativeCubicTo(cX - dx, 0f, cX, dy, cX, cY)

    // Bottom-right curve
    relativeCubicTo(0f, cY - dy, -dx, cY, -cX, cY)

    // Bottom-left curve
    relativeCubicTo(-(cX - dx), 0f, -cX, -dy, -cX, -cY)

    // Top-left curve
    relativeCubicTo(0f, -(cY - dy), dx, -cY, cX, -cY)

    close()
}