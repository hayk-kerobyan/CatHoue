package com.example.cathouse.features.cats.layers.data.model.remote

import com.google.gson.annotations.SerializedName

data class CatDto(
    @SerializedName("_id") val id: String,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("mimetype") val mimetype: String,
    @SerializedName("tags") val tags: List<String>
)
