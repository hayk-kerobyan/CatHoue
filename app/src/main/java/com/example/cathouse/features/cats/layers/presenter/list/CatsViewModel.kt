package com.example.cathouse.features.cats.layers.presenter.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.cathouse.features.cats.layers.domain.repo.CatRepo

class CatsViewModel(catRepo: CatRepo) : ViewModel() {

    val state = catRepo.getCatsPagingSource().cachedIn(viewModelScope)

}