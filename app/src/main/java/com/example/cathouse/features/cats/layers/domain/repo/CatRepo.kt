package com.example.cathouse.features.cats.layers.domain.repo

import androidx.paging.PagingData
import com.example.cathouse.features.cats.layers.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepo {
    fun getCatsPagingSource(): Flow<PagingData<Cat>>
    fun getByExternalId(externalId: String): Flow<Cat?>
}