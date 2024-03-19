package com.example.cathouse.features.cats.layers.presenter.details.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import com.example.cathouse.R
import com.example.cathouse.common.ui.theme.dimens
import com.example.cathouse.features.cats.layers.domain.model.Cat
import com.example.cathouse.features.cats.layers.presenter.list.composables.LottieAnimationForRes

@Composable
fun CatDetailsContent(
    cat: Cat,
    paddingValues: PaddingValues,
    loader: ImageLoader
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
            .verticalScroll(rememberScrollState())
    ) {
        val tagsLabel = cat.tags.joinToString()
        SubcomposeAsyncImage(
            imageLoader = loader,
            modifier = Modifier
                .fillMaxWidth(),
            model = cat.imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.cd_cat_image, tagsLabel),
            loading = {
                LottieAnimationForRes(R.raw.loading)
            },
            error = {
                LottieAnimationForRes(R.raw.error_image_loading)
            }
        )
        Text(
            modifier = Modifier.padding(MaterialTheme.dimens.attributeSizes.medium),
            text = stringResource(R.string.label_tags, tagsLabel)
        )
    }
}