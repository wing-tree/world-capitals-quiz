package wing.tree.world.capitals.quiz.data.extension

fun <T> MutableSet<T>.toggle(element: T) {
    if (contains(element)) {
        remove(element)
    } else {
        add(element)
    }
}
