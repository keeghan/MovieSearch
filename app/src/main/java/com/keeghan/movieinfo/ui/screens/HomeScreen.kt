package com.keeghan.movieinfo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeghan.movieinfo.models.MovieOverViewResponse
import com.keeghan.movieinfo.ui.components.CardImage
import com.keeghan.movieinfo.utils.SmallSpaceH
import com.keeghan.movieinfo.viewModel.SearchViewModel

@Composable
fun HomeScreen(
    modifier: Modifier,
    productsViewModel: SearchViewModel = hiltViewModel()
) {
    var isButtonClicked by remember { mutableStateOf(false) }

   // val movieResponse by productsViewModel.popularMovieResponse.observeAsState()

    Column(modifier = Modifier.then(modifier)) {
        Text(text = "Popular Movies")
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                6.dp, Alignment.CenterHorizontally
            )
        ) {
//            movieResponse?.let {
//                items(count = it.size) { loc ->
//                    PopularMovieCard(movie = movieResponse!![loc])
//                }
//            }
        }
//        if (movieResponse?.isEmpty() == true) {
//            OutlinedButton(onClick = { isButtonClicked = true }) {
//                Text(text = "Reload")
//            }
//        }
    }
}

@Composable
fun PopularMovieCard(movie: MovieOverViewResponse) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(270.dp)
    ) {
        Column {
            CardImage(url = movie?.title?.image?.url, title = movie.title.title)
            Column(
                Modifier.padding(start = 4.dp, end = 4.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                SmallText(text = movie.title.type)
                Text(
                    movie.title.toString(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 13.sp,
                )
                SmallSpaceH()
                Row {
                    SmallText(text = movie.title.year.toString())
                    SmallText(text = movie.releaseDate)
                }
            }
        }
    }
}