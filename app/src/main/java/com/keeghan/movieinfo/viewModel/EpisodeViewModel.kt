package com.keeghan.movieinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class EpisodeViewModel @Inject constructor(
    @Named("mainRepository") private val repository: EpisodeRepository,
    //@Named("ioDispatcher") private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        SearchUiState(
            searchState = ApiCallState.IDLE,
            filters = Filters(
                false,
                false,
                false,
                false,
                false,
                false,
                false,
            )        )
    )
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    //Todo: Maybe integrate data into uiState
    private val _movieSearchResult = MutableStateFlow<PagingData<Result>>(PagingData.empty())
    val movieSearchResult = _movieSearchResult as Flow<PagingData<Result>>


    //Search Movie Function
    private fun searchMovie(title: String, types: String?) {
        viewModelScope.launch {
            repository.findTitle(title, types).cachedIn(viewModelScope).collect { pagingData ->
                _movieSearchResult.value = pagingData
                _uiState.update { it.copy(searchState = ApiCallState.SUCCESS) }
            }
        }
    }

    //handle search from Ui
    fun search(title: String, types: String?) {
        _uiState.update { it.copy(searchState = ApiCallState.LOADING) }
        if (title.isBlank()) {
            _movieSearchResult.value = PagingData.empty()
            _uiState.update { it.copy(searchState = ApiCallState.ERROR) }
            return
        }
        searchMovie(title, types)
    }

    /*Search Filter*/
    private fun search(title: String) {
        _uiState.update { it.copy(searchState = ApiCallState.LOADING) }
        if (title.isBlank()) {
            _movieSearchResult.value = PagingData.empty()
            _uiState.update { it.copy(searchState = ApiCallState.ERROR) }
            return
        }
        val types = listOfNotNull(
            if (_uiState.value.filters.movieFilter) "movie" else null,
            if (_uiState.value.filters.shortFilter) "short" else null,
            if (_uiState.value.filters.tvSeriesFilter) "tvSeries" else null,
            if (_uiState.value.filters.videoGameFilter) "videoGame" else null,
            if (_uiState.value.filters.tvMovieFilter) "tvMovie" else null,
            if (_uiState.value.filters.tvEpisodeFilter) "tvEpisode" else null,
            if (_uiState.value.filters.tvMiniSeriesFilter) "tvMiniSeries" else null,
        ).joinToString(",")
        searchMovie(title, types)
    }

    fun searchWithFilters(title: String, filters: Filters) {
        _uiState.update { it.copy(filters = filters) }
        search(title)
    }

    fun reverseFilter() {
        _uiState.update {
            it.copy(
                filters = Filters(
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                )
            )
        }
    }

}

data class Filters(
    val movieFilter: Boolean,
    val shortFilter: Boolean,
    val tvSeriesFilter: Boolean,
    val videoGameFilter: Boolean,
    val tvMovieFilter: Boolean,
    val tvEpisodeFilter: Boolean,
    val tvMiniSeriesFilter: Boolean,
)

//Keep track of state of Api calls
data class SearchUiState(
    val searchState: ApiCallState, val filters: Filters
)
