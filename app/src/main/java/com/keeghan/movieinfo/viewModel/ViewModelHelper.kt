package com.keeghan.movieinfo.viewModel

/**
 * Class that contains all the filters that can be applied
 * when searching for a movie/shows.
 * Passed as part of [SearchUiState]
 * */
data class Filters(
    val movieFilter: Boolean,
    val shortFilter: Boolean,
    val tvSeriesFilter: Boolean,
    val videoGameFilter: Boolean,
    val tvMovieFilter: Boolean,
    val tvEpisodeFilter: Boolean,
    val tvMiniSeriesFilter: Boolean,
)

/**
 * Keep track of the state of Api calls and
 * applied search filters in [SearchViewModel]
 **/
data class SearchUiState(
    val searchState: ApiCallState,
    val filters: Filters,
    val errorHandler: ErrorHandler
)

data class ErrorHandler(
    val msg: String = "",
    val isShown: Boolean = true
)