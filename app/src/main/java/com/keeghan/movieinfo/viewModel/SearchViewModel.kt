package com.keeghan.movieinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keeghan.movieinfo.models.shows.Result
import com.keeghan.movieinfo.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModel @Inject constructor(
    @Named("mainRepository") private val repository: EpisodeRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var debounceJob: Job? = null
    private var requestId = 0
    private val searchRequest = MutableStateFlow(SearchRequest())

    val movieSearchResult: Flow<PagingData<Result>> = searchRequest
        .flatMapLatest { request ->
            if (request.query.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                repository.findTitle(request.query, request.filters.toApiValue())
            }
        }
        .cachedIn(viewModelScope)


    fun onQueryChanged(query: String) {
        _uiState.update {
            it.copy(
                query = query,
                isDebouncing = query.isNotBlank(),
                isBlankQueryError = false
            )
        }
        debounceJob?.cancel()
        searchRequest.value = SearchRequest()

        if (query.isBlank()) {
            _uiState.update { it.copy(hasSearched = false, isDebouncing = false) }
            return
        }

        debounceJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_MILLIS)
            search(query, _uiState.value.filters)
        }
    }

    private fun search(query: String, filters: Filters) {
        _uiState.update { it.copy(hasSearched = true, isDebouncing = false) }
        searchRequest.value = SearchRequest(query.trim(), filters, ++requestId)
    }

    fun searchWithFilters(filters: Filters) {
        debounceJob?.cancel()
        _uiState.update { it.copy(filters = filters) }
        val query = _uiState.value.query
        if (query.isNotBlank()) search(query, filters)
    }

    fun submitSearch() {
        val query = _uiState.value.query
        if (query.isBlank()) {
            _uiState.update { it.copy(isBlankQueryError = true) }
            return
        }

        debounceJob?.cancel()
        val filters = Filters()
        _uiState.update {
            it.copy(filters = filters, isDebouncing = false, isBlankQueryError = false)
        }
        search(query, filters)
    }

    fun retrySearch() {
        val state = _uiState.value
        if (state.query.isNotBlank()) search(state.query, state.filters)
    }

    private fun Filters.toApiValue(): String = listOfNotNull(
        if (movieFilter) "movie" else null,
        if (shortFilter) "short" else null,
        if (tvSeriesFilter) "tvSeries" else null,
        if (videoGameFilter) "videoGame" else null,
        if (tvMovieFilter) "tvMovie" else null,
        if (tvEpisodeFilter) "tvEpisode" else null,
        if (tvMiniSeriesFilter) "tvMiniSeries" else null,
    ).joinToString(",")

    private companion object {
        const val SEARCH_DEBOUNCE_MILLIS = 300L
    }

    private data class SearchRequest(
        val query: String = "",
        val filters: Filters = Filters(),
        val id: Int = 0
    )
}

