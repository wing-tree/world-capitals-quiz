package wing.tree.world.capitals.quiz.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import wing.tree.world.capitals.quiz.ui.compose.Summary
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import wing.tree.world.capitals.quiz.R
import wing.tree.world.capitals.quiz.constant.Preferences
import wing.tree.world.capitals.quiz.constant.ShadowElevation
import wing.tree.world.capitals.quiz.data.constant.EMPTY
import wing.tree.world.capitals.quiz.data.constant.ONE
import wing.tree.world.capitals.quiz.data.constant.ONE_HUNDRED
import wing.tree.world.capitals.quiz.data.constant.SLASH
import wing.tree.world.capitals.quiz.data.constant.THIRTY
import wing.tree.world.capitals.quiz.data.constant.WorldCapitals
import wing.tree.world.capitals.quiz.data.extension.float
import wing.tree.world.capitals.quiz.data.extension.isNull
import wing.tree.world.capitals.quiz.data.extension.milliseconds
import wing.tree.world.capitals.quiz.data.model.History
import wing.tree.world.capitals.quiz.extension.format
import wing.tree.world.capitals.quiz.extension.gradient
import wing.tree.world.capitals.quiz.extension.toQuiz
import wing.tree.world.capitals.quiz.ui.compose.Empty
import wing.tree.world.capitals.quiz.ui.compose.Icon
import wing.tree.world.capitals.quiz.ui.compose.Loading
import wing.tree.world.capitals.quiz.ui.compose.LocalActivity
import wing.tree.world.capitals.quiz.ui.compose.LocalNavController
import wing.tree.world.capitals.quiz.ui.compose.ShareQuizDialog
import wing.tree.world.capitals.quiz.ui.state.HistoryUiState
import wing.tree.world.capitals.quiz.ui.theme.CoolMint
import wing.tree.world.capitals.quiz.ui.theme.WorldCapitalsQuizTheme
import wing.tree.world.capitals.quiz.viewmodel.HistoryViewModel

class HistoryFragment : BaseFragment() {
    private val viewModel by viewModels<HistoryViewModel> {
        ViewModelProvider.AndroidViewModelFactory(application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WorldCapitalsQuizTheme {
                    CompositionLocalProvider(
                        LocalActivity provides requireActivity(),
                        LocalNavController provides findNavController(),
                    ) {
                        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                        AnimatedContent(
                            targetState = uiState,
                            modifier = Modifier.fillMaxSize(),
                            transitionSpec = {
                                fadeIn() togetherWith fadeOut()
                            },
                            contentKey = {
                                it::class
                            },
                            label = EMPTY
                        ) { targetState ->
                            when (targetState) {
                                HistoryUiState.Loading -> Loading(modifier = Modifier.fillMaxSize())

                                is HistoryUiState.Content -> {
                                    Content(
                                        content = targetState,
                                        onClick = { action ->
                                            when (action) {
                                                HistoryUiState.Action.Back -> {
                                                    viewModel.select(null)
                                                }

                                                is HistoryUiState.Action.Select -> {
                                                    lifecycleScope.launch {
                                                        val timeMillis = ONE_HUNDRED.plus(THIRTY).milliseconds

                                                        delay(timeMillis)
                                                        viewModel.select(action.history)
                                                    }
                                                }

                                                is HistoryUiState.Action.Favorite -> {
                                                    lifecycleScope.launch {
                                                        Preferences.Favorites.toggle(requireContext(), action.key)
                                                    }
                                                }

                                                is HistoryUiState.Action.Delete -> {
                                                    viewModel.delete(action.history)
                                                }
                                            }
                                        },
                                        modifier = Modifier.fillMaxSize(),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    content: HistoryUiState.Content,
    onClick: (HistoryUiState.Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navController = LocalNavController.current
    val deleteTarget = remember {
        mutableStateOf<History?>(null)
    }

    val openShareQuizDialog = remember {
        mutableStateOf(false)
    }

    BackHandler(content is HistoryUiState.Content.Summary) {
        onClick(HistoryUiState.Action.Back)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Title(content)
                },
                modifier = Modifier.shadow(elevation = ShadowElevation),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            when (content) {
                                is HistoryUiState.Content.Histories -> navController.popBackStack()
                                is HistoryUiState.Content.Summary -> onClick(HistoryUiState.Action.Back)
                            }
                        },
                    ) {
                        Icon(imageVector = Icons.Rounded.ArrowBack)
                    }
                },
                actions = {
                    Actions(content = content, openDialog = openShareQuizDialog)
                },
            )
        },
    ) { innerPadding ->
        val activity = LocalActivity.current

        AnimatedContent(
            targetState = content,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            contentKey = {
                it::class
            },
            label = EMPTY
        ) { targetState ->
            when (targetState) {
                is HistoryUiState.Content.Histories -> Histories(
                    histories = targetState,
                    onItemClick = {
                        onClick(HistoryUiState.Action.Select(it))
                    },
                    onItemLongClick = {
                        deleteTarget.value = it
                    },
                    modifier = Modifier.fillMaxSize(),
                )

                is HistoryUiState.Content.Summary -> {
                    Summary(
                        answerReviews = targetState.history.answerReviews,
                        favorites = targetState.favorites,
                        onItemClick = {
                            onClick(HistoryUiState.Action.Favorite(it))
                        },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }

        DeleteDialog(
            target = deleteTarget,
            onClick = {
                onClick(HistoryUiState.Action.Delete(it))
            }
        )

        ShareQuizDialog(
            openDialog = openShareQuizDialog,
            onItemClick = {
                if (content is HistoryUiState.Content.Summary) {
                    val text = content.history.toQuiz(activity, it)

                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, text)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)

                    activity.startActivity(shareIntent)
                }
            }
        )
    }
}

@Composable
private fun Title(
    content: HistoryUiState.Content,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        targetState = content,
        modifier = modifier,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        },
        contentKey = {
            content::class
        },
        label = EMPTY
    ) { targetState ->
        when (targetState) {
            is HistoryUiState.Content.Histories -> Text(text = stringResource(id = R.string.histories))
            is HistoryUiState.Content.Summary -> {
                with(targetState.history) {
                    Text(text = "$score $SLASH ${difficulty.count}")
                }
            }
        }
    }
}

@Composable
private fun Actions(
    content: HistoryUiState.Content,
    openDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier,
) {
    val visible = content is HistoryUiState.Content.Summary

    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        IconButton(
            onClick = {
                openDialog.value = true
            },
        ) {
            Icon(imageVector = Icons.Rounded.Share)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Histories(
    histories: HistoryUiState.Content.Histories,
    onItemClick: (History) -> Unit,
    onItemLongClick: (History) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        targetState = histories.value.isEmpty(),
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        },
        label = EMPTY
    ) { targetState ->
        if (targetState) {
            Empty(text = stringResource(id = R.string.no_history_available))
        } else {
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    items = histories.value,
                    key = {
                        it.primaryKey
                    }
                ) { history ->
                    History(
                        history = history,
                        onClick = onItemClick,
                        onLongClick = onItemLongClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement(),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun History(
    history: History,
    onClick: (History) -> Unit,
    onLongClick: (History) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    ElevatedCard(
        modifier = modifier
            .clip(CardDefaults.elevatedShape)
            .combinedClickable(
                onClick = {
                    onClick(history)
                },
                onLongClick = {
                    onLongClick(history)
                },
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column(
                modifier = Modifier.weight(ONE.float),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val map = remember {
                    WorldCapitals.nations.associateBy { nation ->
                        nation.key
                    }
                }

                val headline = history.answerReviews.joinToString { answerReview ->
                    val country = map[answerReview.correctAnswer.key]?.country

                    country?.let {
                        context.getString(it)
                    } ?: EMPTY
                }

                with(history) {
                    Text(
                        text = headline,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = ONE,
                    )

                    Text(
                        text = "$score $SLASH ${answerReviews.count()}",
                        color = colorScheme.onSurfaceVariant,
                        style = typography.labelLarge,
                    )
                }
            }

            Text(
                text = history.timestamp.format(context),
                color = colorScheme.onSurfaceVariant,
                style = typography.labelMedium,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DeleteDialog(
    target: MutableState<T?>,
    onClick: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    val value = target.value

    if (value.isNull()) {
        return
    }

    AlertDialog(
        onDismissRequest = {
            target.value = null
        }
    ) {
        Surface(
            modifier = modifier.padding(28.dp),
            shape = CircleShape,
        ) {
            val coroutineScope = rememberCoroutineScope()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .gradient(persistentListOf(CoolMint, Color.White))
                    .padding(12.dp)
                    .clip(CircleShape)
                    .clickable {
                        coroutineScope.launch {
                            onClick(value)

                            val timeMillis = ONE_HUNDRED.plus(THIRTY).milliseconds

                            delay(timeMillis)
                            target.value = null
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.delete),
                    modifier = Modifier.padding(vertical = 12.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
