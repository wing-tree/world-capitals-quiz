package wing.tree.world.capitals.quiz.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import wing.tree.world.capitals.quiz.R

val KotraHope = FontFamily(
    Font(R.font.kotra_hope)
)

val Typography = with(Typography()) {
    Typography(
        displayMedium = displayMedium.copy(fontFamily = KotraHope),
        titleLarge = titleLarge.copy(fontFamily = KotraHope),
        titleMedium = titleMedium.copy(fontFamily = KotraHope),
        bodyLarge = bodyLarge.copy(fontFamily = KotraHope),
        labelSmall = labelSmall.copy(fontFamily = KotraHope),
    )
}
