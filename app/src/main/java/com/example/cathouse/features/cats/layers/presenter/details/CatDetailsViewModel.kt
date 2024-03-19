package com.example.cathouse.features.cats.layers.presenter.details

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cathouse.R
import com.example.cathouse.common.dispatchers.DispatcherProvider
import com.example.cathouse.features.cats.layers.domain.model.Cat
import com.example.cathouse.features.cats.layers.domain.repo.CatRepo
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatDetailsViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val catRepo: CatRepo
) : ViewModel() {

    private val _state = MutableStateFlow(createState())

    /**
     * Exposed state
     */
    val state = _state.asStateFlow()


    private val _events = Channel<CatDetailsEvent>()

    /**
     * Exposed flow to send one-time action events
     */
    val events = _events.receiveAsFlow()


    fun onItemSelected(id: String?) {
        job?.cancel()
        id?.let { loadCat(it) }
            ?: _state.update { it.copy(selectedId = null, cat = null) }
    }

    private var job: Job? = null
    private fun loadCat(externalId: String) {
        job = catRepo.getByExternalId(externalId)
            .onStart { _state.update { it.copy(selectedId = externalId, isLoading = true) } }
            .onEach { cat ->
                _state.update { it.copy(cat = cat, isLoading = false) }
            }
            .catch { handleError(it, R.string.error_load_cat_details) }
            .flowOn(dispatcherProvider.IO)
            .launchIn(viewModelScope)

    }

    private suspend fun handleError(throwable: Throwable, @StringRes message: Int) {
        throwable.printStackTrace()
        _state.update { it.copy(isLoading = false) }
        _events.send(OnError(message))
    }

    private fun createState() = CatDetailsState(
        selectedId = null,
        cat = null,
        isLoading = false
    )
}


/**
 * Interface defining one-time action events sent by ViewModel to be executed on UI
 */
sealed interface CatDetailsEvent
data class OnError(@StringRes val message: Int) : CatDetailsEvent

data class CatDetailsState(
    val selectedId: String?,
    val cat: Cat?,
    val isLoading: Boolean,
)