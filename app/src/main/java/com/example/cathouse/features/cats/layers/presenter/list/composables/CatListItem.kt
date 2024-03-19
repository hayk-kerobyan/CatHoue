package com.example.cathouse.features.cats.layers.presenter.list.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import com.example.cathouse.R
import com.example.cathouse.common.ui.theme.dimens
import com.example.cathouse.features.cats.layers.domain.model.Cat

@Composable
fun CatListItemContainer(
    onClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Surface(
        shadowElevation = MaterialTheme.dimens.attributeSizes.small,
        shape = RoundedCornerShape(MaterialTheme.dimens.attributeSizes.medium),
        modifier = Modifier
            .padding(MaterialTheme.dimens.attributeSizes.medium)
            .height(MaterialTheme.dimens.listItemSize)
            .clickable { onClick() },


        ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}

@Composable
fun CatListLoadingItem() {
    CatListItemContainer {
        LottieScreen(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.attributeSizes.large),
            resId = R.raw.loading
        )
    }
}

@Composable
fun CatListErrorItem(
    onClick: () -> Unit,
) {
    CatListItemContainer {
        LottieScreen(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.attributeSizes.medium)
                .clickable { onClick() },
            resId = R.raw.error_append,
            autoReplay = false
        )
    }
}

@Composable
fun CatListImageItem(
    cat: Cat,
    onClick: (Cat) -> Unit,
    imageLoader: ImageLoader
) {
    CatListItemContainer(
        onClick = { onClick(cat) }
    ) {
        SubcomposeAsyncImage(
            imageLoader = imageLoader,
            modifier = Modifier
                .size(MaterialTheme.dimens.listItemImageSize)
                .clip(CircleShape),
            model = imageOfWidth(cat.imageUrl, MaterialTheme.dimens.listItemImageSize),

            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.cd_cat_image, cat.tags.joinToString()),
            loading = {
                CircularProgressIndicator(modifier = Modifier.wrapContentSize())
            },
            error = {
                LottieAnimationForRes(R.raw.error_image_loading)
            }
        )
    }
}

fun imageOfWidth(imageUrl: String, dimen: Dp) = buildString {
    append(imageUrl)
    append("?width=")
    append(dimen.value)
}
