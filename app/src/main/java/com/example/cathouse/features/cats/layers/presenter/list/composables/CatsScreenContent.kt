package com.example.cathouse.features.cats.layers.presenter.list.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.ImageLoader
import com.example.cathouse.R
import com.example.cathouse.common.ui.theme.dimens
import com.example.cathouse.features.cats.layers.domain.model.Cat
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CatsScreenContent(
    modifier: Modifier,
    gridState: LazyGridState,
    contentPadding: PaddingValues,
    pagingItems: LazyPagingItems<Cat>,
    onCatSelected: (Cat) -> Unit,
    imageLoader: ImageLoader
) {

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = pagingItems.loadState.refresh is LoadState.Loading
    )

    SwipeRefresh(state = swipeRefreshState, onRefresh = { pagingItems.refresh() }) {
        if (pagingItems.itemCount == 0) {
            EmptyListScreen(modifier, pagingItems)
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                if (pagingItems.loadState.refresh is LoadState.Error) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.dimens.attributeSizes.large),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.error_refresh_failed),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                CatsLazyVerticalGrid(
                    modifier = modifier,
                    gridState = gridState,
                    contentPadding = contentPadding,
                    pagingItems = pagingItems,
                    onCatSelected = onCatSelected,
                    imageLoader = imageLoader
                )
            }
        }
    }
}