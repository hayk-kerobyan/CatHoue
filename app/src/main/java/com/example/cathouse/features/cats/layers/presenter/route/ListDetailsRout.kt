package com.example.cathouse.features.cats.layers.presenter.route

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cathouse.features.cats.layers.presenter.details.CatDetailsScreen
import com.example.cathouse.features.cats.layers.presenter.details.CatDetailsViewModel
import com.example.cathouse.features.cats.layers.presenter.list.CatsScreen
import com.example.cathouse.features.cats.layers.presenter.list.CatsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CatListDetailsRout(windowSizeClass: WindowSizeClass) {
    val viewModel = koinViewModel<CatsViewModel>()
    val detailsViewModel = koinViewModel<CatDetailsViewModel>()

    val pagingItems = viewModel.state.collectAsLazyPagingItems()
    val detailsState by detailsViewModel.state.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()

    if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            CatsScreen(
                modifier = Modifier.weight(0.3f),
                gridState,
                pagingItems = pagingItems,
                onCatSelected = {
                    detailsViewModel.onItemSelected(it.externalId)
                }
            )
            CatDetailsScreen(
                modifier = Modifier.weight(0.7f),
                state = detailsState,
                events = detailsViewModel.events
            )
        }
    } else {
        if (detailsState.selectedId == null) {
            CatsScreen(
                modifier = Modifier.fillMaxSize(),
                gridState,
                pagingItems = pagingItems,
                onCatSelected = {
                    detailsViewModel.onItemSelected(it.externalId)
                }
            )
        } else {
            BackHandler {
                detailsViewModel.onItemSelected(null)
            }
            CatDetailsScreen(
                modifier = Modifier.fillMaxSize(),
                state = detailsState,
                events = detailsViewModel.events
            )
        }
    }
}