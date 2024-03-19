package com.example.cathouse.features.cats.layers.domain.model

data class Cat(
    val externalId: String,
    val databaseId: Long,
    val imageUrl: String,
    val tags: List<String>
)