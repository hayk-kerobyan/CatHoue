package com.example.cathouse.features.cats.layers.presenter.details

import app.cash.turbine.test
import com.example.cathouse.common.dispatchers.TestDispatcherProvider
import com.example.cathouse.common.rules.MainDispatcherRule
import com.example.cathouse.features.cats.catsTestData
import com.example.cathouse.features.cats.layers.data.repo.FakeCatRepoImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CatDetailsViewModelTest {

    lateinit var viewModel: CatDetailsViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        val repo = FakeCatRepoImpl().apply { cats.addAll(catsTestData) }
        val dispatcherProvider = TestDispatcherProvider()

        viewModel = CatDetailsViewModel(dispatcherProvider, repo)
    }


    @Test
    fun `assert ViewModel state is changed in correct order`() = runTest {
        val selectedItem = catsTestData.first()
        val testResults = mutableListOf<CatDetailsState>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.toList(testResults)
        }
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState.cat).isNull()
            assertThat(initialState.selectedId).isNull()
            assertThat(initialState.isLoading).isFalse()
        }

        viewModel.onItemSelected(selectedItem.externalId)

        viewModel.state.test {
            val loadingState = awaitItem()
            assertThat(loadingState.cat).isNull()
            assertThat(loadingState.selectedId).isEqualTo(selectedItem.externalId)
            assertThat(loadingState.isLoading).isTrue()

            val finalState = awaitItem()
            assertThat(finalState.cat).isEqualTo(selectedItem)
            assertThat(finalState.selectedId).isEqualTo(selectedItem.externalId)
            assertThat(finalState.isLoading).isFalse()
        }
    }

    @Test
    fun `assert ViewModel state is reset correctly`() = runTest {
        val selectedItem = catsTestData.first()
        viewModel.onItemSelected(selectedItem.externalId)
        val testResults = mutableListOf<CatDetailsState>()

        delay(200)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.toList(testResults)
        }
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState.cat).isEqualTo(selectedItem)
            assertThat(initialState.selectedId).isEqualTo(selectedItem.externalId)
            assertThat(initialState.isLoading).isFalse()
        }

        viewModel.onItemSelected(null)

        viewModel.state.test {
            val finalState = awaitItem()
            assertThat(finalState.cat).isNull()
            assertThat(finalState.selectedId).isNull()
            assertThat(finalState.isLoading).isFalse()
        }
    }


    @Test
    fun `assert ViewModel initial state is correct`() = runTest {
        val initialState = viewModel.state.value
        assertThat(initialState.cat).isNull()
        assertThat(initialState.selectedId).isNull()
        assertThat(initialState.isLoading).isFalse()
    }


    @Test
    fun `assert ViewModel returns correct state when existing item id is selected`() = runTest {
        val selectedItemId = "1"
        viewModel.onItemSelected(selectedItemId)
        val state = viewModel.state.value
        assertThat(state.cat).isNotNull()
        assertThat(state.cat?.externalId).isEqualTo(selectedItemId)
        assertThat(state.selectedId).isEqualTo(selectedItemId)
        assertThat(state.isLoading).isFalse()
    }

    @Test
    fun `assert ViewModel returns correct state when null is selected`() = runTest {
        viewModel.onItemSelected(null)
        val state = viewModel.state.value
        assertThat(state.cat).isNull()
        assertThat(state.selectedId).isNull()
        assertThat(state.isLoading).isFalse()
    }

    @Test
    fun `assert ViewModel returns correct state when not existing item is selected`() = runTest {
        val selectedItemId = "1000"
        viewModel.onItemSelected(selectedItemId)
        val state = viewModel.state.value
        assertThat(state.cat).isNull()
        assertThat(state.selectedId).isEqualTo(selectedItemId)
        assertThat(state.isLoading).isFalse()
    }
}