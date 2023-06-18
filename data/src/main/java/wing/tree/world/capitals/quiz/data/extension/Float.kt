package wing.tree.world.capitals.quiz.data.extension

import wing.tree.world.capitals.quiz.data.constant.STRAIGHT_ANGLE
import wing.tree.world.capitals.quiz.data.constant.TWO
import kotlin.math.PI
import kotlin.math.pow

val Float.half: Float get() = div(TWO)
val Float.radians: Double get() = div(STRAIGHT_ANGLE).times(PI)

fun Float.square() = pow(TWO)
