package com.example.cathouse.features.cats.layers.presenter.list.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.cathouse.R
import com.example.cathouse.features.cats.layers.domain.model.Cat

@Composable
fun EmptyListScreen(
    modifier: Modifier,
    pagingItems: LazyPagingItems<Cat>
) {
    when (val state = pagingItems.loadState.refresh) { //FIRST LOAD
        is LoadState.Error -> {
            if (pagingItems.itemCount == 0) {
                LottieScreen(modifier = modifier, resId = R.raw.error_refresh) {
                    Button(onClick = { pagingItems.refresh() }) {
                        Text(text = stringResource(R.string.retry))
                    }
                }
            }
        }

        is LoadState.Loading -> { // Loading UI
            if (pagingItems.itemCount == 0) {
                LottieScreen(modifier = Modifier.fillMaxSize(), resId = R.raw.loading)
            }
        }

        is LoadState.NotLoading -> {
            if (pagingItems.itemCount == 0 && state.endOfPaginationReached) {
                LottieScreen(modifier = Modifier.fillMaxSize(), resId = R.raw.empty_box)
            }
        }
    }
}