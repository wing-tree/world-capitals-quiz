package wing.tree.world.capitals.quiz.ui.compose

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import wing.tree.world.capitals.quiz.R
import wing.tree.world.capitals.quiz.constant.ContentAlpha
import wing.tree.world.capitals.quiz.constant.StarSize
import wing.tree.world.capitals.quiz.data.extension.`is`
import wing.tree.world.capitals.quiz.data.model.AnswerReview
import wing.tree.world.capitals.quiz.ui.theme.RedditRed
import wing.tree.world.capitals.quiz.ui.theme.WhatsAppLightGreen
import java.util.regex.Pattern

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Summary(
    answerReviews: ImmutableList<AnswerReview>,
    favorites: ImmutableSet<String>,
    onItemClick: (key: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(answerReviews) {
            val answer = it.answer
            val correctAnswer = it.correctAnswer
            val country = stringResource(id = correctAnswer.country)
            val favorited = favorites.contains(correctAnswer.key)
            val regex = with("|$country") {
                "(?=:$this)|(?<=:$this)"
            }

            val text = buildAnnotatedString {
                stringResource(
                    id = R.string.format,
                    country,
                )
                    .split(Pattern.compile(regex)).forEach { match ->
                        if (match `is` country) {
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                append(match)
                            }
                        } else {
                            append(match)
                        }
                    }
            }

            var favoritedState by rememberSaveable {
                mutableStateOf(favorited)
            }

            Box(modifier = Modifier.fillMaxSize()) {
                val containerColor by animateColorAsState(
                    targetValue = if (favoritedState) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )

                ElevatedCard(
                    onClick = {
                        favoritedState = favoritedState.not()
                        onItemClick(correctAnswer.key)
                    },
                    colors = CardDefaults.elevatedCardColors(containerColor = containerColor),
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(text = text)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = stringResource(id = answer.capital.capital),
                                color = when (answer) {
                                    correctAnswer -> WhatsAppLightGreen
                                    else -> RedditRed
                                },
                            )

                            Text(text = stringResource(id = correctAnswer.capital.capital))
                        }
                    }
                }

                IconButton(
                    onClick = {
                        favoritedState = favoritedState.not()
                        onItemClick(correctAnswer.key)
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
    }
}
