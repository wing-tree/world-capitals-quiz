package wing.tree.world.capitals.quiz.data.extension

import wing.tree.world.capitals.quiz.data.constant.ONE_HUNDRED
import wing.tree.world.capitals.quiz.data.constant.TWO
import wing.tree.world.capitals.quiz.data.constant.ZERO

val Int.float: Float get() = toFloat()
val Int.half: Int get() = div(TWO)
val Int.hundreds: Int get() = times(ONE_HUNDRED)
val Int.isNegative: Boolean get() = this < ZERO
val Int.long: Long get() = toLong()
val Int.milliseconds: Long get() = long
