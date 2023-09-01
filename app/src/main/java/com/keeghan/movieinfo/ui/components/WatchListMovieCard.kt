package com.keeghan.movieinfo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.keeghan.movieinfo.ui.screens.SmallText
import com.keeghan.movieinfo.ui.theme.cardSurfaceVariant
import com.keeghan.movieinfo.ui.theme.seed
import com.keeghan.movieinfo.utils.SmallSpaceH
import com.keeghan.movieinfo.utils.SpaceH

@Composable
fun WatchListMovieCard(
    ratings: String,
    title: String,
    startYear: String,
    endYear: String,
    url: String,
    runTime: String
) {
    Card(
        modifier = Modifier
            .height(280.dp)
            .padding(end = 10.dp, bottom = 15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
            pressedElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(
                    url
                ).diskCachePolicy(CachePolicy.ENABLED).build(),
                contentDescription = title,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.height(190.dp)
            )
            Column(
                Modifier
                    .padding(start = 10.dp, end = 4.dp)
                    .height(90.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                SpaceH(side = 2.dp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star, contentDescription = "ratings", tint = seed,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(end = 5.dp)
                    )
                    SmallText(text = ratings)
                }

                Spacer(modifier = Modifier.weight(1f))
                Box {
                    Text(
                        title,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 13.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row {
                    SmallText(text = startYear)
                    SmallText(text = endYear)
                    SmallText(text = runTime)
                }
                SmallSpaceH()
            }
        }
    }
}

@Preview
@Composable
fun WatchListMovieCardPreview() {
    WatchListMovieCard("34", "John wick", "2033", "", "", "2h 34m")
}
