package wing.tree.world.capitals.quiz.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import wing.tree.world.capitals.quiz.AdFreeChecker
import wing.tree.world.capitals.quiz.InterstitialAdLoader
import wing.tree.world.capitals.quiz.R
import wing.tree.world.capitals.quiz.constant.Preferences
import wing.tree.world.capitals.quiz.constant.ShadowElevation
import wing.tree.world.capitals.quiz.constant.noOperations
import wing.tree.world.capitals.quiz.data.constant.BLANK
import wing.tree.world.capitals.quiz.data.constant.ONE
import wing.tree.world.capitals.quiz.data.constant.RIGHT_ANGLE
import wing.tree.world.capitals.quiz.data.constant.SEVEN
import wing.tree.world.capitals.quiz.data.constant.SLASH
import wing.tree.world.capitals.quiz.data.constant.TWO
import wing.tree.world.capitals.quiz.data.extension.float
import wing.tree.world.capitals.quiz.data.extension.hundreds
import wing.tree.world.capitals.quiz.data.extension.`is`
import wing.tree.world.capitals.quiz.data.extension.isNotNull
import wing.tree.world.capitals.quiz.data.extension.milliseconds
import wing.tree.world.capitals.quiz.extension.gradient
import wing.tree.world.capitals.quiz.extension.toQuiz
import wing.tree.world.capitals.quiz.model.Question
import wing.tree.world.capitals.quiz.ui.compose.DoubleBackHandler
import wing.tree.world.capitals.quiz.ui.compose.HorizontalSpacer
import wing.tree.world.capitals.quiz.ui.compose.Icon
import wing.tree.world.capitals.quiz.ui.compose.Loading
import wing.tree.world.capitals.quiz.ui.compose.NumberText
import wing.tree.world.capitals.quiz.ui.compose.ShareQuizDialog
import wing.tree.world.capitals.quiz.ui.compose.Summary
import wing.tree.world.capitals.quiz.ui.state.QuizUiState
import wing.tree.world.capitals.quiz.ui.state.QuizUiState.Action
import wing.tree.world.capitals.quiz.ui.state.QuizUiState.Content
import wing.tree.world.capitals.quiz.ui.theme.SkyBlue
import wing.tree.world.capitals.quiz.ui.theme.SpanishSkyBlue
import wing.tree.world.capitals.quiz.ui.theme.WorldCapitalsQuizTheme
import wing.tree.world.capitals.quiz.viewmodel.QuizViewModel
import wing.tree.world.capitals.quiz.viewmodel.factory.QuizViewModelFactory
import java.util.regex.Pattern

class QuizFragment : BaseFragment() {
    private val adFreeChecker = AdFreeChecker()
    private val interstitialAdLoader = InterstitialAdLoader()
    private val viewModel by viewModels<QuizViewModel> {
        val navArgs by navArgs<QuizFragmentArgs>()

        QuizViewModelFactory(
            application = requireActivity().application,
            difficulty = navArgs.difficulty,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        interstitialAdLoader.load(requireContext())

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WorldCapitalsQuizTheme {
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    val openDialog = remember {
                        mutableStateOf(false)
                    }

                    Content(
                        uiState = uiState,
                        onClick = { action ->
                            lifecycleScope.launch {
                                if (adFreeChecker.check(requireContext()).not()) {
                                    when (action) {
                                        Action.Home, Action.Replay -> interstitialAdLoader.show(
                                            requireActivity()
                                        )

                                        is Action.DoubleBack -> if (action.uiState.isSummary()) {
                                            interstitialAdLoader.show(requireActivity())
                                        }

                                        else -> noOperations
                                    }
                                }
                            }

                            when (action) {
                                Action.Back -> Toast.makeText(
                                    requireContext(),
                                    getString(R.string.press_the_back_button_once_more_to_exit),
                                    Toast.LENGTH_SHORT,
                                ).show()

                                Action.Histories -> {
                                    val directions = QuizFragmentDirections
                                        .actionQuizFragmentToHistoriesFragment()

                                    navigate(directions)
                                }

                                Action.Home -> popBackStack()
                                Action.Replay -> viewModel.replay()

                                is Action.Complete -> viewModel.complete(action.inProgress)
                                is Action.DoubleBack -> popBackStack()
                                is Action.Share -> {
                                    openDialog.value = true
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize(),
                    )

                    ShareQuizDialog(
                        openDialog = openDialog,
                        onItemClick = {
                            val summary = uiState

                            if (summary is Content.Summary) {
                                val text = summary.history.toQuiz(context, it)

                                val sendIntent: Intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, text)
                                    type = "text/plain"
                                }

                                val shareIntent = Intent.createChooser(sendIntent, null)

                                requireActivity().startActivity(shareIntent)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun Content(
    uiState: QuizUiState,
    onClick: (Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        targetState = uiState,
        modifier = modifier,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        },
        contentKey = {
            when (it) {
                QuizUiState.Loading -> QuizUiState.Loading::class
                is Content -> Content::class
            }
        }
    ) { targetState ->
        DoubleBackHandler(
            onBack = {
                onClick(Action.Back)
            },
            onDoubleBack = {
                onClick(Action.DoubleBack(targetState))
            },
        )

        when (targetState) {
            QuizUiState.Loading -> Loading(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
            )

            is Content -> Content(
                content = targetState,
                onClick = onClick,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    content: Content,
    onClick: (Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Row {
                            val number = with(content) {
                                when (this) {
                                    is Content.InProgress -> round.intValue.inc()
                                    is Content.Summary -> score
                                }
                            }

                            NumberText(number = number, digits = TWO)
                            Text(text = "$BLANK$SLASH$BLANK")
                            NumberText(number = content.count, digits = TWO)
                        }
                    }
                },
                modifier = Modifier.shadow(elevation = ShadowElevation),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onClick(Action.DoubleBack(content))
                        },
                    ) {
                        Icon(imageVector = Icons.Rounded.ArrowBack)
                    }
                },
                actions = {
                    AnimatedVisibility(
                        visible = content.isSummary(),
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        Row {
                            IconButton(
                                onClick = {
                                    if (content is Content.Summary) {
                                        onClick(Action.Share(content.history))
                                    }
                                },
                            ) {
                                Icon(imageVector = Icons.Rounded.Share)
                            }

                            IconButton(
                                onClick = {
                                    onClick(Action.Histories)
                                },
                            ) {
                                Icon(painter = painterResource(id = R.drawable.round_history_24))
                            }
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        AnimatedContent(
            targetState = content,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            contentKey = {
                it.key
            },
        ) { targetState ->
            when (targetState) {
                is Content.InProgress -> InProgress(
                    inProgress = targetState,
                    onClick = onClick,
                )

                is Content.Summary -> {
                    val context = LocalContext.current
                    val coroutineScope = rememberCoroutineScope()

                    Summary(
                        summary = targetState,
                        onClick = onClick,
                        onItemClick = { key ->
                            coroutineScope.launch {
                                Preferences.Favorites.toggle(
                                    context = context,
                                    value = key,
                                )
                            }
                        }
                    )
                }
            }
        }

    }
}

@Composable
private fun InProgress(
    inProgress: Content.InProgress,
    onClick: (Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    val questions = inProgress.questions
    val count = questions.count()

    var round by inProgress.round

    val question = questions[round.coerceAtMost(count.dec())]
    val answered by remember(round) {
        derivedStateOf {
            question.answer.value.isNotNull()
        }
    }

    Column(modifier = modifier) {
        val progress by animateFloatAsState(
            targetValue = round.inc().div(count.float),
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            strokeCap = StrokeCap.Round,
        )

        AnimatedContent(
            targetState = question,
            modifier = Modifier.weight(ONE.float),
            transitionSpec = {
                val towards = if (targetState.round > initialState.round) {
                    AnimatedContentTransitionScope.SlideDirection.Left
                } else {
                    AnimatedContentTransitionScope.SlideDirection.Right
                }

                slideIntoContainer(
                    towards = towards,
                    animationSpec = tween()
                ) togetherWith slideOutOfContainer(
                    towards = towards,
                    animationSpec = tween()
                )
            }
        ) { targetState ->
            ProvideTextStyle(value = typography.titleLarge) {
                Question(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    question = targetState,
                )
            }
        }

        Surface(modifier = Modifier.fillMaxWidth(), shadowElevation = ShadowElevation) {
            Button(
                onClick = {
                    if (round < count.dec()) {
                        round += ONE
                    } else {
                        onClick(Action.Complete(inProgress))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                enabled = answered,
                shape = ShapeDefaults.Medium,
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.next),
                    style = typography.titleLarge,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Question(
    question: Question,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val options = question.options

        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            val country = stringResource(id = options[question.correctAnswer].country)
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
                            withStyle(style = SpanStyle(color = colorScheme.primary)) {
                                append(match)
                            }
                        } else {
                            append(match)
                        }
                    }
            }

            Text(
                text = text,
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 16.dp,
                ),
            )
        }

        Column(
            modifier = Modifier
                .weight(ONE.float)
                .wrapContentHeight()
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            options.forEachIndexed { index, option ->
                val selected = index `is` question.answer.value
                val outlineWidth by animateDpAsState(
                    targetValue = if (selected) {
                        TWO.dp
                    } else {
                        ONE.dp
                    }
                )

                val outlineColor by animateColorAsState(
                    targetValue = if (selected) {
                        colorScheme.primary
                    } else {
                        colorScheme.outline
                    }
                )

                val containerColor by animateColorAsState(
                    targetValue = if (selected) {
                        colorScheme.primaryContainer
                    } else {
                        colorScheme.surface
                    }
                )

                val border = BorderStroke(outlineWidth, outlineColor)

                OutlinedCard(
                    onClick = {
                        question.answer.value = index
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = containerColor,
                    ),
                    border = border
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 8.dp,
                                top = 12.dp,
                                end = 16.dp,
                                bottom = 12.dp,
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = selected,
                            onClick = {
                                question.answer.value = index
                            },
                        )

                        HorizontalSpacer(width = 4.dp)
                        Text(text = stringResource(id = option.capital.capital))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Summary(
    summary: Content.Summary,
    onClick: (Action) -> Unit,
    onItemClick: (key: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        var enabled by rememberSaveable {
            mutableStateOf(false)
        }

        LaunchedEffect(enabled) {
            if (enabled.not()) {
                delay(SEVEN.hundreds.milliseconds)

                enabled = true
            }
        }

        Summary(
            answerReviews = summary.history.answerReviews,
            favorites = summary.favorites,
            onItemClick = onItemClick,
            modifier = Modifier.weight(ONE.float),
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = ShadowElevation,
        ) {
            AnimatedContent(
                targetState = enabled,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
            ) { targetState ->
                if (targetState) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        ElevatedCard(
                            onClick = {
                                onClick(Action.Home)
                            },
                            modifier = Modifier.weight(ONE.float),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .gradient(
                                        persistentListOf(SkyBlue, SpanishSkyBlue),
                                        RIGHT_ANGLE,
                                    )
                                    .padding(horizontal = 24.dp, vertical = 12.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = stringResource(id = R.string.home),
                                    color = colorScheme.onPrimary,
                                    style = typography.titleLarge,
                                )
                            }
                        }

                        ElevatedCard(
                            onClick = {
                                onClick(Action.Replay)
                            },
                            modifier = Modifier.weight(ONE.float),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .gradient(
                                        persistentListOf(SkyBlue, SpanishSkyBlue),
                                        RIGHT_ANGLE,
                                    )
                                    .padding(horizontal = 24.dp, vertical = 12.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = stringResource(id = R.string.replay),
                                    color = colorScheme.onPrimary,
                                    style = typography.titleLarge,
                                )
                            }
                        }
                    }
                } else {
                    Loading(modifier = Modifier.padding(vertical = 24.dp))
                }
            }
        }
    }
}
