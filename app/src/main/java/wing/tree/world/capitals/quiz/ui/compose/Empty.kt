package wing.tree.world.capitals.quiz.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import wing.tree.world.capitals.quiz.data.constant.NOT_FOUND

@Composable
fun Empty(
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = NOT_FOUND,
            style = MaterialTheme.typography.displayMedium,
        )

        VerticalSpacer(height = 16.dp)
        Text(text = text)
    }
}