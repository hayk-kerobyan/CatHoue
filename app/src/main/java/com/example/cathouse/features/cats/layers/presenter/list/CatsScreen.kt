package com.example.cathouse.features.cats.layers.presenter.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import com.example.cathouse.common.ui.theme.AppTheme
import com.example.cathouse.common.ui.theme.dimens
import com.example.cathouse.di.appModule
import com.example.cathouse.di.networkModule
import com.example.cathouse.features.cats.di.catsModule
import com.example.cathouse.features.cats.layers.domain.model.Cat
import com.example.cathouse.features.cats.layers.presenter.list.composables.CatsScreenContent
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin

@Composable
fun CatsScreen(
    modifier: Modifier,
    gridState:LazyGridState,
    pagingItems: LazyPagingItems<Cat>,
    onCatSelected: (Cat) -> Unit,
    imageLoader: ImageLoader = getKoin().get()
) {
    Scaffold(
        modifier = modifier,
    ) {
        CatsScreenContent(
            modifier = Modifier.fillMaxSize(),
            gridState = gridState,
            contentPadding = PaddingValues(
                top = it.calculateTopPadding()+MaterialTheme.dimens.attributeSizes.medium,
                bottom = it.calculateBottomPadding(),
                start = MaterialTheme.dimens.attributeSizes.medium,
                end = MaterialTheme.dimens.attributeSizes.medium
            ),
            pagingItems = pagingItems,
            onCatSelected = onCatSelected,
            imageLoader = imageLoader
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview
@Composable
fun CatScreenPreview() {
    val context = LocalContext.current.applicationContext
    val config = LocalConfiguration.current
    KoinApplication(application = {
        // If you need Context
        androidContext(context)
        modules(appModule, networkModule, catsModule)
    }) {
        AppTheme(
            windowSizeClass = WindowSizeClass.calculateFromSize(
                DpSize(
                    config.screenWidthDp.dp,
                    config.screenHeightDp.dp
                )
            )
        ) {
            CatsScreen(
                modifier = Modifier.fillMaxSize(),
                gridState = rememberLazyGridState(),
                pagingItems = MutableStateFlow(
                    PagingData.from(
                        data = mutableListOf<Cat>().apply {
                            repeat(9) {
                                add(
                                    Cat(
                                        externalId = "$it",
                                        databaseId = it.toLong(),
                                        imageUrl = "https://cataas.com/cat/ePjD3dUht1wKH6A2",
                                        tags = emptyList()
                                    )
                                )
                            }
                        },
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.Error(IllegalArgumentException()),
                            prepend = LoadState.NotLoading(false),
                            append = LoadState.NotLoading(true),
                        )
                    )
                ).collectAsLazyPagingItems(),
                onCatSelected = {},
                imageLoader = ImageLoader.Builder(context).build()
            )
        }
    }
}


