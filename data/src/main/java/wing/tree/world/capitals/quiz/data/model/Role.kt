package wing.tree.world.capitals.quiz.data.model

import androidx.annotation.StringRes
import wing.tree.world.capitals.quiz.data.R

enum class Role(@StringRes val role: Int) {
    ADMINISTRATIVE(role = R.string.administrative),
    COMMERCIAL(role = R.string.commercial),
    CLAIMED(role = R.string.claimed),
    DE_FACTO(role = R.string.de_facto),
    JUDICIAL(role = R.string.judicial),
    LEGISLATIVE(role = R.string.legislative),
    OFFICIAL(role = R.string.official);
}
