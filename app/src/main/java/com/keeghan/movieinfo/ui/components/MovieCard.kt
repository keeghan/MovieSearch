package com.keeghan.movieinfo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.keeghan.movieinfo.models.shows.Image
import com.keeghan.movieinfo.models.shows.ImageX
import com.keeghan.movieinfo.models.shows.ParentTitle
import com.keeghan.movieinfo.models.shows.Result
import com.keeghan.movieinfo.repository.paging.extractId
import com.keeghan.movieinfo.ui.screens.SmallText
import com.keeghan.movieinfo.utils.SmallSpaceH

/**
 * A composable that represents a movie search item
 * @param movie is an object of type [Result] that
 * models a show/Movie/Series received from the Api
 * */
@Composable
fun MovieCard(
    movie: Result,
    onMovieClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(280.dp)
            .clickable { onMovieClick(extractId(movie.id)!!) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp
        )
    ) {
        Column {
            CardImage(url = movie?.image?.url, title = movie.title)
            Column(
                Modifier
                    .padding(start = 4.dp, end = 4.dp)
                    .height(90.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                SmallText(text = movie.titleType)
                Spacer(modifier = Modifier.weight(1f))
                Box {
                    Text(
                        movie.title,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 13.sp,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row {
                    SmallText(text = movie.year.toString())
                    SmallText(text = movie.year.toString())
                }
                SmallSpaceH()
            }
        }
    }
}


@Preview
@Composable
fun CardPreview2() {
    val img = Image(100, "def", "", 4000)
    val imgx = ImageX(100, "def", "", 4000)
    val pt = ParentTitle("23", imgx, "Heero times", "show", 2003)

    val movie = Result(
        12,
        "3",
        img,
        "23",
        4,
        pt,
        "null",
        emptyList(),
        4,
        3,
        2002,
        2003,
        "Honk Kong - Madness of the goat",
        "tvShows",
        2004
    )
    MovieCard(movie = movie) {}
}