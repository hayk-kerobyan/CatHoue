package com.example.cathouse.features.cats.layers.presenter.list.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.ImageLoader
import com.example.cathouse.R
import com.example.cathouse.common.ui.theme.dimens
import com.example.cathouse.features.cats.layers.domain.model.Cat

private const val GridSpanCount = 2

@Composable
fun CatsLazyVerticalGrid(
    modifier: Modifier,
    gridState: LazyGridState,
    contentPadding: PaddingValues,
    pagingItems: LazyPagingItems<Cat>,
    onCatSelected: (Cat) -> Unit,
    imageLoader: ImageLoader
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = gridState,
        columns = GridCells.Fixed(GridSpanCount),
        contentPadding = contentPadding
    ) {

        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.externalId },
            contentType = pagingItems.itemContentType { "Cats" },
        ) { index ->
            pagingItems[index]?.let { cat ->
                CatListImageItem(
                    cat = cat,
                    onClick = onCatSelected,
                    imageLoader = imageLoader
                )
            }
        }

        when (val state = pagingItems.loadState.append) { // Pagination
            is LoadState.Error -> {
                item {
                    CatListErrorItem {
                        pagingItems.retry()
                    }
                }
            }

            is LoadState.Loading -> { // Pagination Loading UI
                item {
                    CatListLoadingItem()
                }
            }

            is LoadState.NotLoading -> {
                if (state.endOfPaginationReached) {
                    item(span = { GridItemSpan(GridSpanCount) }) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(MaterialTheme.dimens.attributeSizes.large),
                            text = stringResource(R.string.label_end_of_list),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}