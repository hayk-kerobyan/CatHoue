package com.example.cathouse.features.cats

import com.example.cathouse.features.cats.layers.domain.model.Cat

fun generateTestData(num:Int) = mutableListOf<Cat>().apply {
    repeat(num) {
        add(
            Cat(
                externalId = "$it",
                databaseId = it.toLong(),
                imageUrl = "https://cataas.com/cat/ePjD3dUht1wKH6A2",
                tags = emptyList()
            )
        )
    }
}