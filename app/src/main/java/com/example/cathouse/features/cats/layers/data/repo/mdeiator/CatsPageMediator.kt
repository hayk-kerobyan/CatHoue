package com.example.cathouse.features.cats.layers.data.repo.mdeiator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.cathouse.db.AppDatabase
import com.example.cathouse.features.cats.layers.data.datasource.local.CatDao
import com.example.cathouse.features.cats.layers.data.datasource.remote.CatApi
import com.example.cathouse.features.cats.layers.data.model.local.CatDb
import com.example.cathouse.features.cats.layers.data.model.local.CatRemoteKey
import com.example.cathouse.features.cats.layers.data.model.toDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalPagingApi::class)
class CatsPageMediator(
    private val context: CoroutineContext,
    private val database: AppDatabase,
    private val catDao: CatDao,
    private val catApi: CatApi
) : RemoteMediator<Int, CatDb>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CatDb>
    ): MediatorResult {
        return withContext(context) {
            try {
                val (loadKey, loadSize) = when (loadType) {
                    LoadType.REFRESH -> null to state.config.initialLoadSize
                    LoadType.PREPEND ->
                        return@withContext MediatorResult.Success(endOfPaginationReached = true)

                    LoadType.APPEND -> {
                        val remoteKey = catDao.getRemoteKeys().firstOrNull()

                        if (remoteKey?.nextKey == null) {
                            return@withContext MediatorResult.Success(endOfPaginationReached = true)
                        }

                        remoteKey.nextKey to state.config.pageSize
                    }
                }

                val response = catApi.getCats(skip = loadKey ?: 0, limit = loadSize)

                val nextKey = if (response.size < loadSize) {
                    null
                } else {
                    (loadKey ?: 0) + response.size
                }

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        catDao.deleteRemoteKey()
                        catDao.deleteAll()
                    }
                    catDao.insertRemoteKey(CatRemoteKey(nextKey = nextKey))
                    catDao.insert(response.map { it.toDb() })
                }

                MediatorResult.Success(
                    endOfPaginationReached = response.size < loadSize
                )
            } catch (e: Exception) {
                e.printStackTrace()
                MediatorResult.Error(e)
            }
        }
    }
}