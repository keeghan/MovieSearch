package com.keeghan.movieinfo.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.keeghan.movieinfo.R

@Composable
fun CardImage(url: String?, title: String) {
    AsyncImage(  //Coil to load images
        model = ImageRequest.Builder(LocalContext.current).data(
            url
        ).crossfade(true).networkCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED).diskCachePolicy(CachePolicy.ENABLED).build(),
        error = (painterResource(R.drawable.ic_launcher_background)),
        placeholder = painterResource(R.drawable.ic_launcher_background),
        contentDescription = title,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
    )
}