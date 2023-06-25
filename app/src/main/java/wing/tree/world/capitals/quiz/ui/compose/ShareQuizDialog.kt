package wing.tree.world.capitals.quiz.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import wing.tree.world.capitals.quiz.R
import wing.tree.world.capitals.quiz.data.constant.BLANK
import wing.tree.world.capitals.quiz.data.constant.ONE_HUNDRED
import wing.tree.world.capitals.quiz.data.constant.THIRTY
import wing.tree.world.capitals.quiz.data.extension.milliseconds
import wing.tree.world.capitals.quiz.extension.gradient
import wing.tree.world.capitals.quiz.ui.theme.CoolMint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareQuizDialog(
    openDialog: MutableState<Boolean>,
    onItemClick: (withAnswers: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (openDialog.value.not()) {
        return
    }

    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        }
    ) {
        Surface(
            modifier = modifier.padding(28.dp),
            shape = ShapeDefaults.ExtraLarge,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .gradient(persistentListOf(CoolMint, Color.White))
                    .padding(12.dp),
            ) {
                Item(
                    withAnswers = false,
                    onClick = onItemClick,
                    modifier = Modifier.fillMaxWidth(),
                )

                Item(
                    withAnswers = true,
                    onClick = onItemClick,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun Item(
    withAnswers: Boolean,
    onClick: (withAnswers: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val text = when (withAnswers) {
        true -> buildString {
            append(stringResource(id = R.string.share_the_quiz))
            append(BLANK)
            append("(${stringResource(id = R.string.with_answers)})")
        }
        else -> stringResource(id = R.string.share_the_quiz)
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable {
                runBlocking {
                    val timeMillis = ONE_HUNDRED.plus(THIRTY).milliseconds

                    delay(timeMillis)
                    onClick(withAnswers)
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 12.dp),
            textAlign = TextAlign.Center,
        )
    }
}
