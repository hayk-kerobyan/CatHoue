package com.example.cathouse.features.cats.layers.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val CAT_REMOTE_KEY_ID = 1L

@Entity("cat_remote_key")
data class CatRemoteKey(
    @PrimaryKey val id: Long = CAT_REMOTE_KEY_ID,
    @ColumnInfo("nextKey") val nextKey: Int?
)