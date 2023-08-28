package com.keeghan.movieinfo.utils

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.keeghan.movieinfo.R
import com.keeghan.movieinfo.ui.components.rememberStarRate
import com.keeghan.movieinfo.ui.components.rememberStarRateEmpty

private const val MAX_BITMAP_SIZE = 10 * 1024 * 1024 // 100 MB

@Composable
fun MovieImageProvider(
    modifier: Modifier = Modifier,
    url: String = "",
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Crop,
    error: Painter? = (painterResource(R.drawable.not_found))
) {
    AsyncImage(  //Coil to load images
        model = ImageRequest.Builder(LocalContext.current).data(
            url
        ).crossfade(true).networkCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED).diskCachePolicy(CachePolicy.ENABLED)
            .build(),
        error = error,
        placeholder = painterResource(R.drawable.ic_launcher_background),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = Modifier.then(modifier)
    )
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MutableRatingStar() {
    var isRated by remember { mutableStateOf(false) }   //User feature not implemented
    val unrated = rememberStarRateEmpty()
    val rated = rememberStarRate(fillColor = MaterialTheme.colorScheme.primary)


    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedContent(targetState = isRated, transitionSpec = {
            if (targetState > initialState) {
                (slideInVertically { height -> height } + fadeIn()).togetherWith(slideOutVertically { height -> -height } + fadeOut())
            } else {
                (slideInVertically { height -> -height } + fadeIn()).togetherWith(slideOutVertically { height -> height } + fadeOut())
            }.using(
                SizeTransform(clip = false)
            )
        }, label = "") {
            Image(if (it) rated else unrated,
                contentDescription = "rate",
                modifier = Modifier
                    .padding(0.dp)
                    .clickable { isRated = !isRated })
        }
    }
}

