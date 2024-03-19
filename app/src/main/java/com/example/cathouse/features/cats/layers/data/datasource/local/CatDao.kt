package com.example.cathouse.features.cats.layers.data.datasource.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cathouse.features.cats.layers.data.model.local.CatDb
import com.example.cathouse.features.cats.layers.data.model.local.CatRemoteKey
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cats: List<CatDb>)

    @Query("SELECT * FROM cats WHERE external_id = :externalId")
    fun getByExternalId(externalId: String): Flow<CatDb?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKey(remoteKey: CatRemoteKey)

    @Query("SELECT * FROM cat_remote_key LIMIT 1")
    fun getRemoteKeys(): List<CatRemoteKey>

    @Query("SELECT * FROM cats")
    fun getPagingSource(): PagingSource<Int, CatDb>

    @Query("DELETE FROM cats")
    suspend fun deleteAll()

    @Query("DELETE FROM cat_remote_key")
    suspend fun deleteRemoteKey()

}