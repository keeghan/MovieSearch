package com.keeghan.movieinfo.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keeghan.movieinfo.models.shows.Result
import com.keeghan.movieinfo.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class SearchViewModel @Inject constructor(
    @Named("mainRepository") private val repository: EpisodeRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        SearchUiState(
            searchState = ApiCallState.IDLE, filters = Filters(
                movieFilter = false,
                shortFilter = false,
                tvSeriesFilter = false,
                videoGameFilter = false,
                tvMovieFilter = false,
                tvEpisodeFilter = false,
                tvMiniSeriesFilter = false,
            ),
            errorHandler = ErrorHandler()
        )
    )
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    //Todo: Maybe integrate data into uiState
    private val _movieSearchResult = MutableStateFlow<PagingData<Result>>(
        PagingData.empty(
            LoadStates(
                refresh = LoadState.NotLoading(true),
                prepend = LoadState.NotLoading(true),
                append = LoadState.NotLoading(true)
            )
        )
    )

    val movieSearchResult = _movieSearchResult as Flow<PagingData<Result>>


    /*Search using current Filters*/
    private fun search(title: String) {
        _uiState.update { it.copy(searchState = ApiCallState.LOADING) }

        //create a list of filters to add to api get query
        val types = listOfNotNull(
            if (_uiState.value.filters.movieFilter) "movie" else null,
            if (_uiState.value.filters.shortFilter) "short" else null,
            if (_uiState.value.filters.tvSeriesFilter) "tvSeries" else null,
            if (_uiState.value.filters.videoGameFilter) "videoGame" else null,
            if (_uiState.value.filters.tvMovieFilter) "tvMovie" else null,
            if (_uiState.value.filters.tvEpisodeFilter) "tvEpisode" else null,
            if (_uiState.value.filters.tvMiniSeriesFilter) "tvMiniSeries" else null,
        ).joinToString(",")
        Log.i("Filters", types)
        viewModelScope.launch {
            repository.findTitle(title, types).cachedIn(viewModelScope).collect { pagingData ->
                _movieSearchResult.value = pagingData
                _uiState.update { it.copy(searchState = ApiCallState.SUCCESS) }
            }
        }
    }

    /*Search method use when a filter button is used */
    fun searchWithFilters(title: String, filters: Filters) {
        _uiState.update { it.copy(filters = filters) }
        search(title)
    }

    /* Clear all filters and Search
    * use full when a new search is conducted by the search Button*/
    fun searchWithBtn(title: String) {
        clearAllFilters()
        search(title)
    }

    fun clearData() {
        _movieSearchResult.value = PagingData.empty(
            LoadStates(
                refresh = LoadState.NotLoading(true),
                prepend = LoadState.NotLoading(true),
                append = LoadState.NotLoading(true)
            )
        )
    }

    /*Clear all filters from search results*/
    private fun clearAllFilters() {
        _uiState.update {
            it.copy(
                filters = Filters(
                    movieFilter = false,
                    shortFilter = false,
                    tvSeriesFilter = false,
                    videoGameFilter = false,
                    tvMovieFilter = false,
                    tvEpisodeFilter = false,
                    tvMiniSeriesFilter = false,
                )
            )
        }
    }

    fun updateErrorHandler() {
        _uiState.update { it.copy(errorHandler = ErrorHandler(isShown = true)) }
    }

}

