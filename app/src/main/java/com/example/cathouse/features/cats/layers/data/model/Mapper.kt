package com.example.cathouse.features.cats.layers.data.model

import com.example.cathouse.BuildConfig
import com.example.cathouse.features.cats.layers.data.model.local.CatDb
import com.example.cathouse.features.cats.layers.data.model.remote.CatDto

fun CatDto.toDb() = CatDb(
    databaseId = 0L,
    externalId = id,
    imageUrl = buildImageUrl(id),
    tags = tags,
)

fun buildImageUrl(id: String) = buildString {
    append(BuildConfig.BASE_URL)
    append("/cat/")
    append(id)
}