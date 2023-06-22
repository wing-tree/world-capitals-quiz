package wing.tree.world.capitals.quiz.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import wing.tree.world.capitals.quiz.R
import wing.tree.world.capitals.quiz.data.constant.EMPTY
import wing.tree.world.capitals.quiz.data.constant.ONE
import wing.tree.world.capitals.quiz.data.extension.float
import wing.tree.world.capitals.quiz.model.InAppProduct
import wing.tree.world.capitals.quiz.ui.compose.Icon
import wing.tree.world.capitals.quiz.ui.compose.Loading
import wing.tree.world.capitals.quiz.ui.compose.LocalActivity
import wing.tree.world.capitals.quiz.ui.compose.LocalNavController
import wing.tree.world.capitals.quiz.ui.state.StoreUiState
import wing.tree.world.capitals.quiz.ui.theme.WorldCapitalsQuizTheme
import wing.tree.world.capitals.quiz.viewmodel.StoreViewModel

class StoreFragment : BaseFragment() {
    private val viewModel by viewModels<StoreViewModel> {
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
                        val activity = LocalActivity.current
                        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                        AnimatedContent(
                            targetState = uiState,
                            transitionSpec = {
                                fadeIn() togetherWith fadeOut()
                            }
                        ) { targetState ->
                            when (targetState) {
                                StoreUiState.Loading -> Loading(
                                    modifier = Modifier.fillMaxSize(),
                                )

                                is StoreUiState.Content -> Content(
                                    content = targetState,
                                    onItemClick = { inAppProduct ->
                                        viewModel.billingService
                                            .launchBillingFlow(
                                                activity = activity,
                                                productDetails = inAppProduct.productDetails,
                                            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    content: StoreUiState.Content,
    onItemClick: (InAppProduct) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navController = LocalNavController.current

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.store))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                    ) {
                        Icon(imageVector = Icons.Rounded.ArrowBack)
                    }
                }
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            items(content.inAppProducts) {
                InAppProduct(
                    inAppProduct = it,
                    onClick = onItemClick,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InAppProduct(
    inAppProduct: InAppProduct,
    onClick: (InAppProduct) -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        onClick = {
            onClick(inAppProduct)
        },
        modifier = modifier,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val productDetails = inAppProduct.productDetails

            inAppProduct.imageResource?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier.size(56.dp),
                )
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(ONE.float),
            ) {
                Text(
                    text = productDetails.name,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = productDetails.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            val formattedPrice = productDetails
                .oneTimePurchaseOfferDetails
                ?.formattedPrice
                ?: EMPTY

            Text(
                text = formattedPrice,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
