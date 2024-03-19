package com.example.cathouse.features.cats.layers.presenter.list

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cathouse.R
import com.example.cathouse.features.cats.generateTestData
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test


class CatsScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun failedRefreshShowsCorrectLabel() {
        rule.setContent {
            val state = rememberLazyGridState()
            CatsScreen(
                modifier = Modifier.fillMaxSize(),
                pagingItems = flow {
                    emit(
                        PagingData.from(
                            data = generateTestData(10),
                            sourceLoadStates = LoadStates(
                                refresh = LoadState.Error(IllegalArgumentException()),
                                prepend = LoadState.NotLoading(false),
                                append = LoadState.NotLoading(false),
                            )
                        )
                    )
                }.collectAsLazyPagingItems(),
                gridState = state,
                onCatSelected = { }
            )
        }
        rule.onNode(
            hasAnyChild( //scaffold container
                hasAnyChild( // swipe refresh layout
                    hasAnyChild( // LazyVerticalGrid
                        hasText(
                            rule.activity.getString(R.string.error_load_cat_details)
                        )
                    )
                )
            )
        )
    }

    @Test
    fun endOfPaginationShowsCorrectMessage() {
        rule.setContent {
            val gridState = rememberLazyGridState()
            CatsScreen(
                modifier = Modifier.fillMaxSize(),
                pagingItems = flow {
                    emit(
                        PagingData.from(
                            data = generateTestData(4),
                            sourceLoadStates = LoadStates(
                                refresh = LoadState.NotLoading(false),
                                prepend = LoadState.NotLoading(false),
                                append = LoadState.NotLoading(true),
                            )
                        )
                    )
                }.collectAsLazyPagingItems(),
                gridState = gridState,
                onCatSelected = { }
            )
        }
        rule.onNode(
            hasAnyChild( //scaffold container
                hasAnyChild( // swipe refresh layout
                    hasAnyChild( // LazyVerticalGrid
                        hasText(
                            rule.activity.getString(R.string.label_end_of_list)
                        )
                    )
                )
            )
        )
    }

}