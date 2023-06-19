package wing.tree.world.capitals.quiz.data.extension

fun Char.digitToIntOrDefault(defaultValue: Int): Int {
    return digitToIntOrNull() ?: defaultValue
}
