package com.example.cathouse.features.cats.utils

fun String.resize(px: Float) = buildString {
    append(this)
    append("?width=")
    append(px)
}