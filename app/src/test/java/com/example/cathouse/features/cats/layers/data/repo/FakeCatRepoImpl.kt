package com.example.cathouse.features.cats.layers.data.repo

import androidx.paging.PagingData
import com.example.cathouse.features.cats.layers.domain.model.Cat
import com.example.cathouse.features.cats.layers.domain.repo.CatRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCatRepoImpl : CatRepo {

    val cats = mutableListOf<Cat>()

    override fun getCatsPagingSource(): Flow<PagingData<Cat>> {
        TODO("Not yet implemented")
    }

    override fun getByExternalId(externalId: String): Flow<Cat?> {
        return flow {
            delay(200)
            emit(
                cats.firstOrNull { it.externalId == externalId }
            )
        }
    }
}