package com.example.cathouse.features.cats.layers.data.datasource.remote

import com.example.cathouse.features.cats.layers.data.model.remote.CatDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("/api/cats")
    suspend fun getCats(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): List<CatDto>

}