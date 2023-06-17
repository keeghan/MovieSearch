package com.keeghan.movieinfo.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keeghan.movieinfo.models.movieOverview.MovieOverViewResponse
import com.keeghan.movieinfo.models.shows.Result
import com.keeghan.movieinfo.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

enum class SearchScreenUiState { LOADING, IDLE }


@HiltViewModel
class EpisodeViewModel @Inject constructor(
    @Named("mainRepository") private val repository: EpisodeRepository,
    @Named("ioDispatcher") private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState = mutableStateOf(SearchScreenUiState.IDLE)
    val uiState = _uiState as State<SearchScreenUiState>

    private var _popularMovies: MutableLiveData<List<MovieOverViewResponse>> = MutableLiveData()
    val popularMovieResponse: LiveData<List<MovieOverViewResponse>> = _popularMovies

    private val _movieSearchResult =
        MutableStateFlow<PagingData<Result>>(PagingData.empty())
    val movieSearchResult = _movieSearchResult as Flow<PagingData<Result>>


//    fun findTitle(title: String): Flow<PagingData<Result>> {
//        Log.i("findTitle", "viewmodel")
//        return repository.findTitle(title).cachedIn(viewModelScope)
//    }

    suspend fun findPopularMovies() {
        Log.i("popular", "viewmodel")
        withContext(dispatcher) {
            _popularMovies.postValue(repository.findPopularTitles())
        }
    }

    //Search Movie Function
    private fun searchMovie(title: String) {
        viewModelScope.launch {
            repository.findTitle(title).cachedIn(viewModelScope).collect {
                _movieSearchResult.value = it
                _uiState.value = SearchScreenUiState.IDLE  //handle loading here
            }
        }
    }

    //handle search from Ui
    fun search(title: String) {
        _uiState.value = SearchScreenUiState.LOADING
        Log.i("EpisodeViewModel", _uiState.value.name)
        if (title.isBlank()) {
            _movieSearchResult.value = PagingData.empty()
            _uiState.value = SearchScreenUiState.IDLE
            return
        }
        searchMovie(title)
    }

}