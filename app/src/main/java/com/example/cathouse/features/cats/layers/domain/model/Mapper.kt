package com.example.cathouse.features.cats.layers.domain.model

import com.example.cathouse.features.cats.layers.data.model.local.CatDb

fun CatDb.toDomain() = Cat(
    databaseId = databaseId,
    externalId = externalId,
    imageUrl = imageUrl,
    tags = tags,
)
