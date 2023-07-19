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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
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
import com.keeghan.movieinfo.viewModel.SearchViewModel

val genres = listOf("movie", "tvSeries", "videoGame", "short", "tvMovie", "tvEpisode", "tvMiniSeries")


/**
 * A composable that represents the searchScreen
 * It receives a flow of Flow<PagingData<Result>> from
 * @param [viewModel], transforms it as lazyPagingItems and
 * displays it in a LazyVerticalGrid. When Items represented by
 * [MovieCard] composable are clicked, their tiles are hoisted up to
 * @param onMovieClick which sends the Id's to the [InfoScreen] composable
 * */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(modifier: Modifier, navController: NavController = rememberNavController(), viewModel: SearchViewModel = hiltViewModel(), onMovieClick: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    var titleState by remember { mutableStateOf(TextFieldValue("")) }
    val uiState = viewModel.uiState.collectAsState()
    val movieResponse = viewModel.movieSearchResult.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }


    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, modifier = Modifier.then(modifier)) { paddingValues ->
        Column(
                Modifier
                        .padding(paddingValues)
                        .padding(10.dp)
                        .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                //  verticalArrangement = Arrangement.Center
        ) {
            val message = uiState.value.errorHandler
            if (!message.isShown) {
                if (message.msg.isNotEmpty()) {
                    Toast.makeText(context, message.msg, Toast.LENGTH_SHORT).show()
                }
                viewModel.updateErrorHandler()
            }

            SpaceH(side = 5.dp)
            OutlinedTextField(
                    value = titleState, onValueChange = { titleState = it },
                    placeholder = { Text("movie name") },
                    label = { Text("Search") },
                    keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Search
                    ),
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "search movie name")
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(50),
                    keyboardActions = KeyboardActions(onSearch = {
                        if (titleState.text.isNotEmpty() && titleState.text.isNotBlank()) {
                            viewModel.searchWithBtn(titleState.text)
                            keyboardController?.hide()
                        } else {
                            Toast.makeText(context, "empty searchBar", Toast.LENGTH_SHORT).show()
                        }
                    }),
                    modifier = Modifier.fillMaxWidth()
            )
            SpaceH(side = 20.dp)


            //check if loading successful
            //Filter Cards(buttons) with different filters , default filter state = false
            if (movieResponse.itemCount > 0) {
                Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    val filter = uiState.value.filters

                    genres.forEach { genre ->
                        GenreFilterCard(
                                genre = genre, isClicked = when (genre) {
                            "movie" -> filter.movieFilter
                            "tvSeries" -> filter.tvSeriesFilter
                            "videoGame" -> filter.videoGameFilter
                            "short" -> filter.shortFilter
                            "tvMovie" -> filter.tvMovieFilter
                            "tvEpisode" -> filter.tvEpisodeFilter
                            "tvMiniSeries" -> filter.tvMiniSeriesFilter
                            else -> false
                        }
                        ) {
                            viewModel.clearData() //empty list first
                            viewModel.searchWithFilters(titleState.text, filter.copy(movieFilter = if (genre == "movie") !filter.movieFilter else filter.movieFilter, tvSeriesFilter = if (genre == "tvSeries") !filter.tvSeriesFilter else filter.tvSeriesFilter, videoGameFilter = if (genre == "videoGame") !filter.videoGameFilter else filter.videoGameFilter, shortFilter = if (genre == "short") !filter.shortFilter else filter.shortFilter, tvMovieFilter = if (genre == "tvMovie") !filter.tvMovieFilter else filter.tvMovieFilter, tvEpisodeFilter = if (genre == "tvEpisode") !filter.tvEpisodeFilter else filter.tvEpisodeFilter, tvMiniSeriesFilter = if (genre == "tvMiniSeries") !filter.tvMiniSeriesFilter else filter.tvMiniSeriesFilter))
                        }
                    }
                }

                SpaceH(side = 20.dp)


                LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalArrangement = Arrangement.SpaceBetween) {
                    items(count = movieResponse.itemCount) {
                        MovieCard(movie = movieResponse[it]!!) { id ->
                            onMovieClick(id)
                        }
                    }

                    // representation of when end of appending new items
                    item {
                        when (val state = movieResponse.loadState.append) {
                            is LoadState.Error -> {
                                val message = when (state.error.message) {
                                    null -> "error"
                                    "no matches" -> "no matches"  //todo fix recurring errors
                                    else -> state.error.message
                                }
                                Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
                            }

                            is LoadState.Loading -> {
                                CircularProgressIndicator()
                            }

                            else -> {}
                        }
                    }
                }

            }
        } //end of lazyRow


        //First time loading (loading from empty state or not appending) and loadFailed Error handling
        when (val state = movieResponse.loadState.refresh) {
            is LoadState.Loading -> {
                Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                }
            }

            //reset PagingSource (to prevent re-propagating errors) and show error
            is LoadState.Error -> {
                val msg = state.error.message
                viewModel.clearData()
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

            }

            else -> {}
        }

    } //End of Main Column
} //End of Scaffold


@Composable
fun SmallText(text: String?) {
    Text(
            text
                    ?: "", color = MaterialTheme.colorScheme.primary, fontSize = 12.sp, modifier = Modifier.padding(end = 5.dp)
    )
}

@Composable
fun GenreFilterCard(genre: String, isClicked: Boolean, onClick: () -> Unit) {
    Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .padding(end = 10.dp)
            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary), shape = RoundedCornerShape(5.dp))
            .padding(start = 5.dp, end = 5.dp, top = 6.dp, bottom = 6.dp)
    ) {
        Text(text = genre, modifier = Modifier
                .clickable {
                    onClick()
                }
                .height(intrinsicSize = IntrinsicSize.Min))
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

    val movie = Result(12, "3", img, "23", 4, pt, "null", emptyList(), 4, 3, 2002, 2003, "Hone Kong - Madness of the got adfsflskfsafo aodifasdfasdkf asdiofjsdfsdf", "tvShows", 2004)
    MovieCard(movie = movie) {}
}