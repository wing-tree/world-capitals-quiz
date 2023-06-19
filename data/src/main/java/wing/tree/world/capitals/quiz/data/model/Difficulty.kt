package wing.tree.world.capitals.quiz.data.model

import wing.tree.world.capitals.quiz.data.constant.FIFTY
import wing.tree.world.capitals.quiz.data.constant.TEN
import wing.tree.world.capitals.quiz.data.constant.THIRTY

enum class Difficulty(val count: Int) {
    EASY(TEN),
    MEDIUM(THIRTY),
    HARD(FIFTY);
}
