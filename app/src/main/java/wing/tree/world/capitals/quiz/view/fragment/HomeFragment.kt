package wing.tree.world.capitals.quiz.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import wing.tree.world.capitals.quiz.R
import wing.tree.world.capitals.quiz.Review
import wing.tree.world.capitals.quiz.data.constant.EMPTY
import wing.tree.world.capitals.quiz.data.constant.FULL_ANGLE
import wing.tree.world.capitals.quiz.data.constant.ONE
import wing.tree.world.capitals.quiz.data.constant.ONE_HUNDRED
import wing.tree.world.capitals.quiz.data.constant.QUARTER
import wing.tree.world.capitals.quiz.data.constant.RIGHT_ANGLE
import wing.tree.world.capitals.quiz.data.constant.THIRTY
import wing.tree.world.capitals.quiz.data.constant.THREE_MINUTES_IN_MILLISECONDS
import wing.tree.world.capitals.quiz.data.constant.ZERO_ANGLE
import wing.tree.world.capitals.quiz.data.extension.float
import wing.tree.world.capitals.quiz.data.extension.milliseconds
import wing.tree.world.capitals.quiz.data.model.Difficulty
import wing.tree.world.capitals.quiz.extension.actionBarSize
import wing.tree.world.capitals.quiz.extension.bounceVertically
import wing.tree.world.capitals.quiz.extension.gradient
import wing.tree.world.capitals.quiz.shareApp
import wing.tree.world.capitals.quiz.ui.compose.LocalActivity
import wing.tree.world.capitals.quiz.ui.compose.VerticalSpacer
import wing.tree.world.capitals.quiz.ui.theme.CoolMint
import wing.tree.world.capitals.quiz.ui.theme.FacebookBlue
import wing.tree.world.capitals.quiz.ui.theme.SpanishSkyBlue
import wing.tree.world.capitals.quiz.ui.theme.WorldCapitalsQuizTheme
import wing.tree.world.capitals.quiz.view.fragment.HomeFragmentDirections.Companion.actionHomeFragmentToQuizFragment

class HomeFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WorldCapitalsQuizTheme {
                    CompositionLocalProvider(LocalActivity provides requireActivity()) {
                        Content(modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Content(modifier: Modifier = Modifier) {
        val coroutineScope = rememberCoroutineScope()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

        ModalNavigationDrawer(
            drawerContent = {
                DrawerContent(
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .clip(DrawerDefaults.shape)
                        .gradient(persistentListOf(CoolMint, Color.White)),
                )
            },
            drawerState = drawerState,
        ) {
            val context = LocalContext.current
            val openDialog = remember {
                mutableStateOf(false)
            }

            Scaffold(
                modifier = modifier.gradient(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = EMPTY)
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        drawerState.open()
                                    }
                                },
                            ) {
                                Icon(imageVector = Icons.Rounded.Menu, contentDescription = null)
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                        ),
                    )
                },
                containerColor = Color.Transparent,
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val infiniteTransition = rememberInfiniteTransition()
                    val rotation by infiniteTransition.animateFloat(
                        initialValue = ZERO_ANGLE,
                        targetValue = FULL_ANGLE,
                        animationSpec = infiniteRepeatable(
                            animation = tween(
                                durationMillis = THREE_MINUTES_IN_MILLISECONDS,
                                easing = LinearEasing,
                            )
                        )
                    )

                    val scale = ONE.plus(QUARTER)

                    Text(
                        text = stringResource(id = R.string.app_name),
                        modifier = Modifier
                            .padding(vertical = 24.dp)
                            .bounceVertically(
                                targetValue = with(LocalDensity.current) {
                                    4.dp.toPx()
                                }
                            ),
                        textAlign = TextAlign.Center,
                        style = typography.displayMedium,
                    )

                    Image(
                        painter = painterResource(id = R.drawable.world_capitals),
                        contentDescription = null,
                        modifier = Modifier
                            .weight(ONE.float)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                rotationZ = rotation
                            },
                    )

                    Column(
                        modifier = Modifier.width(IntrinsicSize.Max),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        ElevatedCard(
                            onClick = {
                                val directions = HomeFragmentDirections
                                    .actionHomeFragmentToWorldCapitalsFragment()

                                navigate(directions)
                            },
                        ) {
                            Box(
                                modifier = Modifier
                                    .gradient(
                                        persistentListOf(SpanishSkyBlue, FacebookBlue),
                                        RIGHT_ANGLE,
                                    ).padding(horizontal = 24.dp, vertical = 12.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = stringResource(id = R.string.world_capitals),
                                    color = colorScheme.onPrimary,
                                    style = typography.titleLarge,
                                )
                            }
                        }

                        ElevatedCard(
                            onClick = {
                                openDialog.value = true
                            },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .gradient(
                                        persistentListOf(SpanishSkyBlue, FacebookBlue),
                                        RIGHT_ANGLE
                                    )
                                    .padding(horizontal = 24.dp, vertical = 12.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = stringResource(id = R.string.quiz),
                                    color = colorScheme.onPrimary,
                                    style = typography.titleLarge,
                                )
                            }
                        }
                    }

                    VerticalSpacer(
                        height = with(LocalDensity.current) {
                            context.actionBarSize.toDp()
                        },
                    )
                }

                DifficultySelectionDialog(
                    openDialog = openDialog,
                    onClick = { difficulty ->
                        val directions = actionHomeFragmentToQuizFragment(difficulty)

                        navigate(directions)
                        openDialog.value = false
                    },
                )
            }
        }
    }

    @Composable
    private fun DrawerContent(modifier: Modifier = Modifier) {
        val context = LocalContext.current

        ModalDrawerSheet(
            modifier = modifier,
            drawerContainerColor = Color.Transparent,
        ) {
            val activity = LocalActivity.current
            val colors = NavigationDrawerItemDefaults.colors(
                unselectedContainerColor = Color.Transparent,
            )

            VerticalSpacer(height = 12.dp)
            NavigationDrawerItem(
                label = {
                    Text(text = stringResource(id = R.string.leave_a_review))
                },
                selected = false,
                onClick = {
                    Review.launchReviewFlow(activity)
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.round_rate_review_24),
                        contentDescription = null,
                    )
                },
                colors = colors,
            )

            NavigationDrawerItem(
                label = {
                    Text(text = stringResource(id = R.string.share_the_app))
                },
                selected = false,
                onClick = {
                    shareApp(context)
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Share,
                        contentDescription = null,
                    )
                },
                colors = colors,
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun DifficultySelectionDialog(
        openDialog: MutableState<Boolean>,
        onClick: (Difficulty) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        if (openDialog.value) {
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
                            .gradient(persistentListOf(CoolMint, Color.White))
                            .padding(12.dp),
                    ) {
                        Difficulty.values().forEach { difficulty ->
                            val text = when (difficulty) {
                                Difficulty.EASY -> stringResource(id = R.string.easy)
                                Difficulty.MEDIUM -> stringResource(id = R.string.medium)
                                Difficulty.HARD -> stringResource(id = R.string.hard)
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(CircleShape)
                                    .clickable {
                                        runBlocking {
                                            val timeMillis = ONE_HUNDRED.plus(THIRTY).milliseconds

                                            delay(timeMillis)
                                            onClick(difficulty)
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
                    }
                }
            }
        }
    }
}
