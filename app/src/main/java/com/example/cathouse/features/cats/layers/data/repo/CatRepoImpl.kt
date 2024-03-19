package com.example.cathouse.features.cats.layers.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.cathouse.common.dispatchers.DispatcherProvider
import com.example.cathouse.db.AppDatabase
import com.example.cathouse.features.cats.layers.data.datasource.local.CatDao
import com.example.cathouse.features.cats.layers.data.datasource.remote.CatApi
import com.example.cathouse.features.cats.layers.data.repo.mdeiator.CatsPageMediator
import com.example.cathouse.features.cats.layers.domain.model.Cat
import com.example.cathouse.features.cats.layers.domain.model.toDomain
import com.example.cathouse.features.cats.layers.domain.repo.CatRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class CatRepoImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val appDatabase: AppDatabase,
    private val catDao: CatDao,
    private val catApi: CatApi
) : CatRepo {

    companion object {
        const val INITIAL_PAGE_SIZE = 31
        const val PAGE_SIZE = 10
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getCatsPagingSource(): Flow<PagingData<Cat>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = INITIAL_PAGE_SIZE,
                pageSize = PAGE_SIZE
            ),
            remoteMediator = CatsPageMediator(dispatcherProvider.IO, appDatabase, catDao, catApi)
        ) {
            catDao.getPagingSource()
        }
            .flow.map { it.map { it.toDomain() } }
    }

    override fun getByExternalId(externalId: String): Flow<Cat?> {
        return catDao.getByExternalId(externalId)
            .map { it?.toDomain() }
            .flowOn(dispatcherProvider.IO)
    }
}