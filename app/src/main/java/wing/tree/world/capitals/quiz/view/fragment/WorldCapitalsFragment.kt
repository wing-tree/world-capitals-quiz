package wing.tree.world.capitals.quiz.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import wing.tree.world.capitals.quiz.R
import wing.tree.world.capitals.quiz.constant.ContentAlpha.medium
import wing.tree.world.capitals.quiz.constant.Preferences
import wing.tree.world.capitals.quiz.constant.ShadowElevation
import wing.tree.world.capitals.quiz.data.constant.FOUR
import wing.tree.world.capitals.quiz.data.constant.ONE
import wing.tree.world.capitals.quiz.data.constant.THREE
import wing.tree.world.capitals.quiz.data.constant.TWO
import wing.tree.world.capitals.quiz.data.constant.WorldCapitals
import wing.tree.world.capitals.quiz.data.constant.WorldCapitals.Africa
import wing.tree.world.capitals.quiz.data.constant.WorldCapitals.America
import wing.tree.world.capitals.quiz.data.constant.WorldCapitals.Asia
import wing.tree.world.capitals.quiz.data.constant.WorldCapitals.Europe
import wing.tree.world.capitals.quiz.data.constant.WorldCapitals.Oceania
import wing.tree.world.capitals.quiz.data.constant.ZERO
import wing.tree.world.capitals.quiz.data.extension.float
import wing.tree.world.capitals.quiz.data.extension.half
import wing.tree.world.capitals.quiz.data.model.Nation
import wing.tree.world.capitals.quiz.extension.stringRes
import wing.tree.world.capitals.quiz.extension.worldCapitals
import wing.tree.world.capitals.quiz.ui.compose.Empty
import wing.tree.world.capitals.quiz.ui.compose.Icon
import wing.tree.world.capitals.quiz.ui.compose.Loading
import wing.tree.world.capitals.quiz.ui.compose.LocalNavController
import wing.tree.world.capitals.quiz.ui.state.WorldCapitalsUiState
import wing.tree.world.capitals.quiz.ui.theme.WorldCapitalsQuizTheme
import wing.tree.world.capitals.quiz.viewmodel.WorldCapitalsViewModel

class WorldCapitalsFragment : BaseFragment() {
    private val viewModel by viewModels<WorldCapitalsViewModel> {
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
                    CompositionLocalProvider(LocalNavController provides findNavController()) {
                        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                        AnimatedContent(
                            targetState = uiState,
                            modifier = Modifier.fillMaxSize(),
                            transitionSpec = {
                                fadeIn() togetherWith fadeOut()
                            },
                            contentKey = {
                                it::class
                            }
                        ) { targetState ->
                            when (targetState) {
                                WorldCapitalsUiState.Loading -> Loading(
                                    modifier = Modifier.fillMaxSize(),
                                )

                                is WorldCapitalsUiState.Content -> Content(
                                    content = targetState,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    content: WorldCapitalsUiState.Content,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val ioDispatcher = Dispatchers.IO
    val lazyListState = rememberLazyListState()
    val navController = LocalNavController.current

    var showOnlyFavorites by rememberSaveable {
        mutableStateOf(false)
    }

    BackHandler(enabled = showOnlyFavorites) {
        showOnlyFavorites = false
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            Surface(shadowElevation = ShadowElevation) {
                Column {
                    CenterAlignedTopAppBar(
                        title = {
                            AnimatedContent(
                                targetState = showOnlyFavorites,
                                transitionSpec = {
                                    if (targetState) {
                                        slideInHorizontally {
                                            it.half
                                        }.plus(fadeIn()) togetherWith slideOutHorizontally {
                                            it.half.unaryMinus()
                                        }.plus(fadeOut())
                                    } else {
                                        slideInHorizontally {
                                            it.half.unaryMinus()
                                        }.plus(fadeIn()) togetherWith slideOutHorizontally {
                                            it.half
                                        }.plus(fadeOut())
                                    }
                                }
                            ) {
                                if (it) {
                                    Text(
                                        text = stringResource(id = R.string.favorites),
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                    )
                                } else {
                                    Title(lazyListState = lazyListState)
                                }
                            }
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    if (showOnlyFavorites) {
                                        showOnlyFavorites = false
                                    } else {
                                        navController.popBackStack()
                                    }
                                },
                            ) {
                                Icon(imageVector = Icons.Rounded.ArrowBack)
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    showOnlyFavorites = showOnlyFavorites.not()
                                },
                            ) {
                                val targetValue = if (showOnlyFavorites) {
                                    colorScheme.primary
                                } else {
                                    colorScheme.onSurfaceVariant.copy(alpha = medium)
                                }

                                val tint by animateColorAsState(targetValue = targetValue)

                                Icon(
                                    imageVector = Icons.Rounded.Star,
                                    tint = tint,
                                )
                            }
                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                    ) {
                        Text(
                            text = stringResource(id = R.string.country),
                            modifier = Modifier.weight(ONE.float),
                            color = colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                        )

                        Text(
                            text = stringResource(id = R.string.capital),
                            modifier = Modifier.weight(ONE.float),
                            color = colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Content(
            showOnlyFavorites = showOnlyFavorites,
            favorites = content.favorites,
            lazyListState = lazyListState,
            onItemClick = { key ->
                coroutineScope.launch(ioDispatcher) {
                    Preferences.Favorites.toggle(context, key)
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        )
    }
}

@Composable
private fun Title(
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
) {
    val firstVisibleItemIndex by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex
        }
    }

    val targetState by remember {
        derivedStateOf {
            when (firstVisibleItemIndex) {
                in indexRange(America) -> America
                in indexRange(Asia) -> Asia
                in indexRange(Europe) -> Europe
                in indexRange(Oceania) -> Oceania
                else -> Africa
            }
        }
    }

    AnimatedContent(
        targetState = targetState,
        modifier = modifier,
        transitionSpec = {
            if (targetState < initialState) {
                slideInVertically {
                    it.unaryMinus()
                }.plus(fadeIn()) togetherWith slideOutVertically {
                    it
                }.plus(fadeOut())
            } else {
                slideInVertically {
                    it
                }.plus(fadeIn()) togetherWith slideOutVertically {
                    it.unaryMinus()
                }.plus(fadeOut())
            }
        },
    ) {
        Text(
            text = stringResource(id = it.continent.stringRes),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun Content(
    showOnlyFavorites: Boolean,
    favorites: ImmutableSet<String>,
    lazyListState: LazyListState,
    onItemClick: (key: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        targetState = showOnlyFavorites,
        modifier = modifier,
        transitionSpec = {
            if (targetState) {
                slideInHorizontally {
                    it.half
                }.plus(fadeIn()) togetherWith slideOutHorizontally {
                    it.half.unaryMinus()
                }.plus(fadeOut())
            } else {
                slideInHorizontally {
                    it.half.unaryMinus()
                }.plus(fadeIn()) togetherWith slideOutHorizontally {
                    it.half
                }.plus(fadeOut())
            }
        },
    ) { targetState ->
        if (targetState) {
            Favorites(
                favorites = favorites,
                onItemClick = onItemClick,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            WorldCapitals(
                favorites = favorites,
                lazyListState = lazyListState,
                onItemClick = onItemClick,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun WorldCapitals(
    favorites: ImmutableSet<String>,
    lazyListState: LazyListState,
    onItemClick: (key: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        listOf(America, Asia, Europe, Oceania, Africa).forEach {
            worldCapitals(
                worldCapitals = it,
                comparator = Nation.comparator(context),
                showOnlyFavorites = false,
                favorites = favorites,
                onItemClick = onItemClick,
            )
        }
    }
}

@Composable
private fun Favorites(
    favorites: ImmutableSet<String>,
    onItemClick: (key: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val rememberFavorites = remember {
        favorites
    }

    if (rememberFavorites.isNotEmpty()) {
        LazyColumn(
            modifier = modifier,
            state = rememberLazyListState(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        ) {
            listOf(America, Asia, Europe, Oceania, Africa).forEach {
                worldCapitals(
                    worldCapitals = it,
                    comparator = Nation.comparator(context),
                    showOnlyFavorites = true,
                    favorites = rememberFavorites,
                    onItemClick = onItemClick,
                )
            }
        }
    } else {
        Empty(
            text = stringResource(id = R.string.there_are_no_favorited_countries),
            modifier = modifier.padding(horizontal = 16.dp),
        )
    }
}

private fun indexRange(worldCapitals: WorldCapitals): IntRange {
    val firstIndex = when (worldCapitals) {
        America -> ZERO
        Asia -> America.count.inc()
        Europe -> America.count.plus(Asia.count).plus(TWO)
        Oceania -> America.count.plus(Asia.count).plus(Europe.count).plus(THREE)
        Africa -> America.count.plus(Asia.count).plus(Europe.count).plus(Oceania.count).plus(FOUR)
    }

    return when (worldCapitals) {
        America -> firstIndex..firstIndex.plus(America.count)
        Asia -> firstIndex..firstIndex.plus(Asia.count)
        Europe -> firstIndex..firstIndex.plus(Europe.count)
        Oceania -> firstIndex..firstIndex.plus(Oceania.count)
        Africa -> firstIndex..firstIndex.plus(Africa.count)
    }
}
