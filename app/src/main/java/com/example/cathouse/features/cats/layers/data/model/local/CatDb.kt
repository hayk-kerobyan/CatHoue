package com.example.cathouse.features.cats.layers.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("cats")
data class CatDb(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("databaseId") val databaseId: Long,
    @ColumnInfo("external_id") val externalId: String,
    @ColumnInfo("image_url") val imageUrl: String,
    @ColumnInfo("tags") val tags: List<String>
)
