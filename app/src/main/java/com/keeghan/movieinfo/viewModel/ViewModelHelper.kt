package com.keeghan.movieinfo.viewModel

/**
 * Class that contains all the filters that can be applied
 * when searching for a movie/shows.
 * Passed as part of [SearchUiState]
 * */
data class Filters(
    val movieFilter: Boolean = false,
    val shortFilter: Boolean = false,
    val tvSeriesFilter: Boolean = false,
    val videoGameFilter: Boolean = false,
    val tvMovieFilter: Boolean = false,
    val tvEpisodeFilter: Boolean = false,
    val tvMiniSeriesFilter: Boolean = false,
)

/**
 * Keep track of the state of Api calls and
 * applied search filters in [SearchViewModel]
 **/
data class SearchUiState(
    val query: String = "",
    val filters: Filters = Filters(),
    val hasSearched: Boolean = false,
    val isDebouncing: Boolean = false,
    val isBlankQueryError: Boolean = false
)
