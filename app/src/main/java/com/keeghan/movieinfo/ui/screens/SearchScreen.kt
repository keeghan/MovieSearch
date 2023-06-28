package com.keeghan.movieinfo.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.keeghan.movieinfo.models.shows.Image
import com.keeghan.movieinfo.models.shows.ImageX
import com.keeghan.movieinfo.models.shows.ParentTitle
import com.keeghan.movieinfo.models.shows.Result
import com.keeghan.movieinfo.ui.components.MovieCard
import com.keeghan.movieinfo.utils.SpaceH
import com.keeghan.movieinfo.viewModel.ApiCallState
import com.keeghan.movieinfo.viewModel.EpisodeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    navController: NavController = rememberNavController(),
    viewModel: EpisodeViewModel = hiltViewModel(),
    onMovieClick: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var titleState by remember { mutableStateOf(TextFieldValue("")) }
    val uiState = viewModel.uiState.collectAsState()
    val movieResponse = viewModel.movieSearchResult.collectAsLazyPagingItems()

    Column(
        Modifier
            .then(modifier)
            .padding(4.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        //  verticalArrangement = Arrangement.Center
    ) {
        SpaceH(side = 5.dp)
        Row(   //Search Bar and button
            modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = titleState,
                onValueChange = {
                    titleState = it
                },
                label = { Text("Movie") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 8.dp)
            )

            FilledTonalIconButton(modifier = Modifier
                .size(48.dp)
                .padding(start = 4.dp), onClick = {
                viewModel.searchWithFilters(
                    titleState.text, uiState.value.filters
                        .copy(false, false, false, false, false, false, false)
                )
                focusManager.clearFocus()
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                //Text(text = "Search")
            }
        }//End of search Bar

        //check if loading successful
        if (uiState.value.searchState == ApiCallState.SUCCESS) {
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                val filter = uiState.value.filters
                GenreFilterCard(genre = "movie", filter.movieFilter) {
                    viewModel.searchWithFilters(
                        titleState.text,
                        filter.copy(movieFilter = !filter.movieFilter)
                    )
                }
                GenreFilterCard(genre = "tvSeries", filter.tvSeriesFilter) {
                    viewModel.searchWithFilters(
                        titleState.text,
                        filter.copy(tvSeriesFilter = !filter.tvSeriesFilter)
                    )
                }
                GenreFilterCard(genre = "videoGame", filter.videoGameFilter) {
                    viewModel.searchWithFilters(
                        titleState.text,
                        filter.copy(videoGameFilter = !filter.videoGameFilter)
                    )
                }
                GenreFilterCard(genre = "short", filter.shortFilter) {
                    viewModel.searchWithFilters(
                        titleState.text,
                        filter.copy(shortFilter = !filter.shortFilter)
                    )
                }
                GenreFilterCard(genre = "tvMovie", filter.tvMovieFilter) {
                    viewModel.searchWithFilters(
                        titleState.text,
                        filter.copy(tvMovieFilter = !filter.tvMovieFilter)
                    )
                }
                GenreFilterCard(genre = "tvEpisode", filter.tvEpisodeFilter) {
                    viewModel.searchWithFilters(
                        titleState.text,
                        filter.copy(tvEpisodeFilter = !filter.tvEpisodeFilter)
                    )
                }
                GenreFilterCard(genre = "tvMiniSeries", filter.tvEpisodeFilter) {
                    viewModel.searchWithFilters(
                        titleState.text,
                        filter.copy(tvEpisodeFilter = !filter.tvEpisodeFilter)
                    )
                }
            }

            SpaceH(side = 25.dp)
            if (movieResponse.itemCount > 0) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
                ) {
                    items(count = movieResponse.itemCount) {
                        MovieCard(movie = movieResponse[it]!!) { id ->
                            onMovieClick(id)
                        }
                    }

                    item {
                        when (val state = movieResponse.loadState.append) {
                            is LoadState.Error -> {
                                val message = when (state.error.message) {
                                    null -> "error"
                                    "no matches" -> ""
                                    else -> state.error.message
                                }
                                Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT)
                                    .show()
                            }

                            is LoadState.Loading -> {
                                CircularProgressIndicator()
                            }

                            else -> {}
                        }
                    }
                }
            } //end of lazyRow

            SpaceH(side = 60.dp)

            //first time Load and error OutPut
            when (val state = movieResponse.loadState.refresh) {
                is LoadState.Error -> {
                    Toast.makeText(
                        LocalContext.current,
                        state.error.message ?: "error",
                        Toast.LENGTH_SHORT
                    ).show()
                viewModel.reverseFilter()  //clear filter when fail
                }

                else -> {}
            }
        }

        if (uiState.value.searchState == ApiCallState.LOADING) {
            CircularProgressIndicator()
        }
    }
}


@Composable
fun SmallText(text: String?) {
    Text(
        text ?: "",
        color = MaterialTheme.colorScheme.primary,
        fontSize = 12.sp,
        modifier = Modifier.padding(end = 5.dp)
    )
}


@Composable
fun GenreFilterCard(genre: String, isClicked: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .padding(end = 10.dp)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(5.dp)
            )
            .padding(start = 5.dp, end = 5.dp, top = 6.dp, bottom = 6.dp)
    ) {
        Text(
            text = genre,
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .height(intrinsicSize = IntrinsicSize.Min)
        )
        AnimatedVisibility(visible = isClicked) {
            Icon(Icons.Default.Check, contentDescription = "filter selected")
        }
    }
    Spacer(modifier = Modifier.width(5.dp))
}


@Preview
@Composable
fun CardPreview() {
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
        "Hone Kong - Madness of the goat",
        "tvShows",
        2004
    )
    MovieCard(movie = movie) {}
}