package wing.tree.world.capitals.quiz.ui.compose

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import wing.tree.world.capitals.quiz.constant.ContentAlpha
import wing.tree.world.capitals.quiz.constant.StarSize
import wing.tree.world.capitals.quiz.data.constant.ONE
import wing.tree.world.capitals.quiz.data.extension.float
import wing.tree.world.capitals.quiz.data.model.Nation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Nation(
    nation: Nation,
    favorited: Boolean,
    onClick: (key: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var favoritedState by remember(favorited) {
        mutableStateOf(favorited)
    }

    val containerColor by animateColorAsState(
        targetValue = if (favoritedState) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        }
    )

    Box(modifier = modifier) {
        ElevatedCard(
            onClick = {
                favoritedState = favoritedState.not()
                onClick(nation.key)
            },
            colors = CardDefaults.elevatedCardColors(containerColor = containerColor),
        ) {
            Row(
                modifier = Modifier
                    .wrapContentHeight(Alignment.CenterVertically)
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = nation.country),
                    modifier = Modifier
                        .weight(ONE.float)
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center,
                )

                Column(
                    modifier = Modifier
                        .weight(ONE.float)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    nation.capitals.forEach { capital ->
                        Column {
                            Text(
                                text = stringResource(id = capital.capital),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                            )

                            capital.role?.let {
                                Text(
                                    text = stringResource(id = it.role),
                                    modifier = Modifier.fillMaxWidth(),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.labelSmall,
                                )
                            }
                        }
                    }
                }
            }
        }

        IconButton(
            onClick = {
                favoritedState = favoritedState.not()
                onClick(nation.key)
            },
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.TopEnd)
                .graphicsLayer {
                    translationX = 16.dp.toPx()
                    translationY = 16.unaryMinus().dp.toPx()
                },
        ) {
            val tint by animateColorAsState(
                targetValue = if (favoritedState) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = ContentAlpha.medium)
                }
            )

            Icon(
                imageVector = Icons.Rounded.Star,
                modifier = Modifier.size(StarSize),
                tint = tint,
            )
        }
    }
}
