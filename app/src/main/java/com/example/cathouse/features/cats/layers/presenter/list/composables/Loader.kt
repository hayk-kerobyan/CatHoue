package com.example.cathouse.features.cats.layers.presenter.list.composables

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cathouse.common.ui.theme.dimens

@Composable
fun LottieScreen(
    modifier: Modifier,
    @RawRes resId: Int,
    autoReplay: Boolean = true,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LottieAnimationForRes(resId, autoReplay)
        content()
    }
}

@Composable
fun LottieAnimationForRes(
    @RawRes resId: Int,
    autoReplay :Boolean = true
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resId))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = if(autoReplay) LottieConstants.IterateForever else 1
    )
    LottieAnimation(
        modifier = Modifier
            .width(MaterialTheme.dimens.emptyScreenIconSize)
            .height(MaterialTheme.dimens.emptyScreenIconSize),
        composition = composition,
        progress = { progress }

    )
}