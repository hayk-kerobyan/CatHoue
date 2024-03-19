package com.example.cathouse.common.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalDimens = compositionLocalOf { Dimens() }

val MaterialTheme.dimens: Dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current

data class Dimens(
    val attributeSizes: AttributeSizes = AttributeSizes(),
    val listItemSize: Dp = 120.dp,
    val listItemImageSize: Dp = 44.dp,
    val coverImageHeight: Dp = 220.dp,
    val emptyScreenIconSize: Dp = 80.dp,
)

//Spacing, corner radius, elevation
data class AttributeSizes(
    val default: Dp = 0.dp,
    val extraSmall: Dp = 2.dp,
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 16.dp,
    val extraLarge: Dp = 24.dp,
)

val CompactDimens = Dimens(
    attributeSizes = AttributeSizes(
        default = 0.dp,
        extraSmall = 2.dp,
        small = 4.dp,
        medium = 8.dp,
        large = 16.dp,
        extraLarge = 24.dp,
    ),
    listItemSize = 120.dp,
    listItemImageSize = 80.dp,
    coverImageHeight = 220.dp,
    emptyScreenIconSize = 80.dp,
)

val MediumDimens = Dimens(
    attributeSizes = AttributeSizes(
        default = 0.dp,
        extraSmall = 4.dp,
        small = 8.dp,
        medium = 16.dp,
        large = 24.dp,
        extraLarge = 32.dp,
    ),
    listItemSize = 140.dp,
    listItemImageSize = 100.dp,
    coverImageHeight = 280.dp,
    emptyScreenIconSize = 140.dp,
)

val ExpandedDimens = Dimens(
    attributeSizes = AttributeSizes(
        default = 0.dp,
        extraSmall = 6.dp,
        small = 10.dp,
        medium = 20.dp,
        large = 28.dp,
        extraLarge = 36.dp,
    ),
    listItemSize = 160.dp,
    listItemImageSize = 110.dp,
    coverImageHeight = 400.dp,
    emptyScreenIconSize = 220.dp,
)