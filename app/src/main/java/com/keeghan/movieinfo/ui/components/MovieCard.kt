package com.keeghan.movieinfo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.keeghan.movieinfo.models.shows.Result
import com.keeghan.movieinfo.repository.paging.extractId
import com.keeghan.movieinfo.ui.screens.SmallText
import com.keeghan.movieinfo.utils.SmallSpaceH


@Composable
fun MovieCard(
    movie: Result,
    onMovieClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(270.dp)
            .clickable { onMovieClick(extractId(movie.id)!!) }
    ) {
        Column {
            CardImage(url = movie?.image?.url, title = movie.title)
            Column(
                Modifier.padding(start = 4.dp, end = 4.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                SmallText(text = movie.titleType)
                Text(
                    movie.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 13.sp,
                )
                SmallSpaceH()
                Row {
                    SmallText(text = movie.year.toString())
                    SmallText(text = movie.year.toString())
                }
            }
        }
    }
}
