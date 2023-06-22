package wing.tree.world.capitals.quiz.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableSet
import wing.tree.world.capitals.quiz.R
import wing.tree.world.capitals.quiz.data.constant.WorldCapitals
import wing.tree.world.capitals.quiz.extension.worldCapitals

@Composable
fun Favorites(
    favorites: ImmutableSet<String>,
    onItemClick: (key: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val rememberFavorites = remember {
        favorites
    }

    if (rememberFavorites.isNotEmpty()) {
        LazyColumn(
            modifier = modifier,
            state = rememberLazyListState(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        ) {
            listOf(WorldCapitals.America, WorldCapitals.Asia, WorldCapitals.Europe, WorldCapitals.Oceania, WorldCapitals.Africa).forEach {
                worldCapitals(
                    worldCapitals = it,
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
