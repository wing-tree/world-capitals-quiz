package wing.tree.world.capitals.quiz.extension

import wing.tree.world.capitals.quiz.data.constant.WorldCapitals

fun WorldCapitals.favorites(set: Set<String>) = nations.filter {
    set.contains(it.key)
}
