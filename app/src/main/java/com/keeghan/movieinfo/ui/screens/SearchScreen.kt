package com.keeghan.movieinfo.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.keeghan.movieinfo.R
import com.keeghan.movieinfo.ui.components.MovieCard
import com.keeghan.movieinfo.utils.SpaceH
import com.keeghan.movieinfo.utils.SpaceW
import com.keeghan.movieinfo.viewModel.SearchViewModel
import androidx.annotation.StringRes

data class GenreOption(
    val apiValue: String,
    @StringRes val labelRes: Int
)

val genres = listOf(
    GenreOption("movie", R.string.type_movie),
    GenreOption("tvSeries", R.string.type_tv_series),
    GenreOption("videoGame", R.string.type_video_game),
    GenreOption("short", R.string.type_short),
    GenreOption("tvMovie", R.string.type_tv_movie),
    GenreOption("tvEpisode", R.string.type_tv_episode),
    GenreOption("tvMiniSeries", R.string.type_tv_miniseries)
)

/**
 * A composable that represents the searchScreen
 * It receives a flow of Flow<PagingData<Result>> from
 * @param [viewModel], transforms it as lazyPagingItems and
 * displays it in a LazyVerticalGrid. When Items represented by
 * [MovieCard] composable are clicked, their tiles are hoisted up to
 * @param onMovieClick which sends the Id's to the [InfoScreen] composable
 * */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    navController: NavController = rememberNavController(),
    viewModel: SearchViewModel = hiltViewModel(),
    onMovieClick: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val uiState by viewModel.uiState.collectAsState()
    val movieResponse = viewModel.movieSearchResult.collectAsLazyPagingItems()

    val lazyVerticalGridState = rememberLazyGridState()

    Column(
        modifier
            .padding(top = 10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        //  verticalArrangement = Arrangement.Center
    ) {
        SpaceH(side = 5.dp)
        OutlinedTextField(
            value = uiState.query,
            onValueChange = viewModel::onQueryChanged,
            placeholder = { Text(stringResource(R.string.movie_name)) },
            label = { Text(text = stringResource(R.string.search), style = MaterialTheme.typography.bodyMedium) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Search
            ),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_movie_name)
                )
            },
            singleLine = true,
            isError = uiState.isBlankQueryError,
            supportingText = if (uiState.isBlankQueryError) {
                { Text(stringResource(R.string.empty_searchbar)) }
            } else {
                null
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = RoundedCornerShape(50),
            keyboardActions = KeyboardActions(onSearch = {
                viewModel.submitSearch()
                keyboardController?.hide()
            }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
        )
        SpaceH(side = 20.dp)

        // Keep filters visible after the first request, including empty/error results.
        if (uiState.hasSearched) {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                val filter = uiState.filters

                genres.forEach { option ->
                    val genre = option.apiValue
                    GenreFilterCard(
                        genre = stringResource(option.labelRes), isClicked = when (genre) {
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
                        viewModel.searchWithFilters(
                            filter.copy(
                                movieFilter = if (genre == "movie") !filter.movieFilter else filter.movieFilter,
                                tvSeriesFilter = if (genre == "tvSeries") !filter.tvSeriesFilter else filter.tvSeriesFilter,
                                videoGameFilter = if (genre == "videoGame") !filter.videoGameFilter else filter.videoGameFilter,
                                shortFilter = if (genre == "short") !filter.shortFilter else filter.shortFilter,
                                tvMovieFilter = if (genre == "tvMovie") !filter.tvMovieFilter else filter.tvMovieFilter,
                                tvEpisodeFilter = if (genre == "tvEpisode") !filter.tvEpisodeFilter else filter.tvEpisodeFilter,
                                tvMiniSeriesFilter = if (genre == "tvMiniSeries") !filter.tvMiniSeriesFilter else filter.tvMiniSeriesFilter
                            )
                        )
                    }
                }
            }

            SpaceH(side = 10.dp)
        }

        val refreshState = movieResponse.loadState.refresh
        when {
            uiState.isDebouncing || refreshState is LoadState.Loading -> {
                SearchLoadingGrid()
            }

            refreshState is LoadState.Error -> {
                SearchStatus(
                    message = refreshState.error.message ?: stringResource(R.string.search_error),
                    actionLabel = stringResource(R.string.retry),
                    onAction = viewModel::retrySearch
                )
            }

            uiState.hasSearched && movieResponse.itemCount == 0 -> {
                SearchStatus(message = stringResource(R.string.no_matches))
            }

            movieResponse.itemCount > 0 -> {
                // Search results
                LazyVerticalGrid(
                    state = lazyVerticalGridState,
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(all = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(count = movieResponse.itemCount) { index ->
                        movieResponse[index]?.let { movie ->
                            MovieCard(movie = movie) { id ->
                                onMovieClick(id)
                            }
                        }
                    }

                    // Representation of loading or failure while appending items.
                    item {
                        when (val state = movieResponse.loadState.append) {
                            is LoadState.Error -> {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(state.error.message ?: stringResource(R.string.search_error))
                                    Button(onClick = movieResponse::retry) {
                                        Text(stringResource(R.string.retry))
                                    }
                                }
                            }

                            is LoadState.Loading -> CircularProgressIndicator()

                            else -> {}
                        }
                    }
                }
            }

            else -> {
                SearchStatus(message = stringResource(R.string.search_prompt))
            }
        }
    }
} //End of SearchScreen

@Composable
private fun SearchLoadingGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(count = 6) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
        }
    }
}

@Composable
private fun SearchStatus(
    message: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, style = MaterialTheme.typography.bodyLarge)
        if (actionLabel != null && onAction != null) {
            SpaceH(8.dp)
            Button(onClick = onAction) {
                Text(actionLabel)
            }
        }
    }
}


@Composable
fun SmallPrimaryText(text: String?) {
    if (text.isNullOrBlank()) return
    Text(
        text,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 12.sp,
        modifier = Modifier.padding(end = 5.dp)
    )
}

@Composable
fun SmallText(text: String?) {
    if (text.isNullOrBlank()) return
    Text(
        text, fontSize = 12.sp, modifier = Modifier.padding(end = 5.dp)
    )
}


@Composable
fun GenreFilterCard(genre: String, isClicked: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .padding(end = 10.dp)
            .selectable(
                selected = isClicked,
                onClick = onClick,
                role = Role.Checkbox
            )
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(5.dp)
            )
            .padding(start = 5.dp, end = 5.dp, top = 6.dp, bottom = 6.dp)
    ) {
        Text(
            text = genre,
            modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)
        )
        AnimatedVisibility(visible = isClicked) {
            Icon(Icons.Default.Check, contentDescription = null)
        }
    }
    SpaceW(side = 5.dp)
}
