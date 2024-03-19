package com.example.cathouse.features.cats.layers.presenter.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.example.cathouse.R
import com.example.cathouse.common.ui.theme.AppTheme
import com.example.cathouse.di.appModule
import com.example.cathouse.di.networkModule
import com.example.cathouse.features.cats.di.catsModule
import com.example.cathouse.features.cats.layers.domain.model.Cat
import com.example.cathouse.features.cats.layers.presenter.details.composables.CatDetailsContent
import com.example.cathouse.features.cats.layers.presenter.list.composables.LottieScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin

@Composable
fun CatDetailsScreen(
    modifier: Modifier,
    state: CatDetailsState,
    events: Flow<CatDetailsEvent>,
    loader: ImageLoader = getKoin().get()
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        val context = LocalContext.current
        LaunchedEffect(true) {
            events.collectLatest { event ->
                when (event) {
                    is OnError -> scope.launch {
                        snackbarHostState.showSnackbar(context.getString(event.message))
                    }
                }
            }
        }

        if (state.isLoading) {
            LottieScreen(modifier = Modifier.fillMaxSize(), resId = R.raw.loading)
        } else if (state.cat == null) {
            LottieScreen(
                modifier = Modifier.fillMaxSize(),
                resId = R.raw.empty_box,
                autoReplay = false
            )
        } else {
            CatDetailsContent(
                cat = state.cat,
                paddingValues = it,
                loader = loader
            )
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview
@Composable
fun CatDetailsScreenPreview() {
    val context = LocalContext.current.applicationContext
    val config = LocalConfiguration.current
    KoinApplication(application = {
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
            CatDetailsScreen(
                modifier = Modifier.fillMaxSize(),
                state = CatDetailsState(
                    cat = Cat(
                        externalId = "1",
                        databaseId = 1L,
                        imageUrl = "",
                        tags = listOf("fluffy", "cute")
                    ),
                    isLoading = false,
                    selectedId = "1"
                ),
                events = flow { },
                loader = ImageLoader.Builder(context).build()
            )
        }
    }
}


