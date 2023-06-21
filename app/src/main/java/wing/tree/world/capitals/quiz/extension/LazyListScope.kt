package wing.tree.world.capitals.quiz.extension

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableSet
import wing.tree.world.capitals.quiz.data.constant.WorldCapitals
import wing.tree.world.capitals.quiz.data.model.Continent
import wing.tree.world.capitals.quiz.ui.compose.Nation
import wing.tree.world.capitals.quiz.ui.compose.VerticalSpacer

fun LazyListScope.worldCapitals(
    worldCapitals: WorldCapitals,
    showOnlyFavorites: Boolean,
    favorites: ImmutableSet<String>,
    onItemClick: (key: String) -> Unit,
) {
    with(worldCapitals) {
        val visible = when (showOnlyFavorites) {
            true -> worldCapitals
                .favorites(favorites)
                .isNotEmpty()

            false -> true
        }

        if (visible) {
            item {
                Continent(continent = continent)
            }
        }

        items(
            items = nations,
            key = {
                it.key
            }
        ) { nation ->
            if (
                when (showOnlyFavorites) {
                    true -> favorites.contains(nation.key)
                    false -> true
                }
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Nation(
                        nation = nation,
                        favorited = favorites.contains(nation.key),
                        onClick = { key ->
                            onItemClick(key)
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )

                    VerticalSpacer(height = 8.dp)
                }
            }
        }
    }
}

@Composable
private fun Continent(
    continent: Continent,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(id = continent.stringRes),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp,
        ),
    )
}
