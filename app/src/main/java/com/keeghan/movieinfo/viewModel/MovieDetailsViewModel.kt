package com.keeghan.movieinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keeghan.movieinfo.models.MovieImagesResponse
import com.keeghan.movieinfo.models.MovieOverViewResponse
import com.keeghan.movieinfo.models.MovieParentalGuideResponse
import com.keeghan.movieinfo.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.async

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    @Named("mainRepository") private val repository: EpisodeRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MovieDetailsUiState())
    val uiState: StateFlow<MovieDetailsUiState> = _uiState.asStateFlow()


    //Get overview and movie images
    fun findOverView(title: String) {
        _uiState.update {
            it.copy(
                overviewState = ApiCallState.LOADING, overview = null, images = null, overviewError = ""
            )
        }

        viewModelScope.launch {
            try {
                // Both requests begin before either one is awaited.
                val overviewRequest = async {
                    repository.findOverView(title)
                }

                val imagesRequest = async {
                    repository.getImages(title)
                }

                val overviewResponse = overviewRequest.await()
                val imagesResponse = imagesRequest.await()

                val overview = overviewResponse.body()
                val images = imagesResponse.body()

                if (overviewResponse.isSuccessful && imagesResponse.isSuccessful && overview != null && images != null) {
                    _uiState.update {
                        it.copy(overviewState = ApiCallState.SUCCESS, overview = overview, images = images)
                    }
                } else {
                    val message = when {
                        !overviewResponse.isSuccessful -> overviewResponse.message()
                        !imagesResponse.isSuccessful -> imagesResponse.message()
                        else -> "The server returned an empty response"
                    }

                    _uiState.update {
                        it.copy(
                            overviewState = ApiCallState.ERROR, overviewError = message
                        )
                    }
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        overviewState = ApiCallState.ERROR, overviewError = exception.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    /*
    * Get parental guidance
    * */
    fun getParentalGuidance(title: String) {
        _uiState.update {
            it.copy(
                parentalGuideState = ApiCallState.LOADING, parentalGuide = null, parentalGuideError = ""
            )
        }
        viewModelScope.launch {
            try {
                val response = repository.getParentalGuide(title)
                val parentalGuide = response.body()
                if (response.isSuccessful && parentalGuide != null) {
                    _uiState.update {
                        it.copy(
                            parentalGuideState = ApiCallState.SUCCESS, parentalGuide = parentalGuide
                        )
                    }
                } else {
                    val message = if (response.isSuccessful) {
                        "The server returned an empty response"
                    } else {
                        response.message()
                    }
                    _uiState.update {
                        it.copy(
                            parentalGuideState = ApiCallState.ERROR, parentalGuideError = message
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        parentalGuideState = ApiCallState.ERROR, parentalGuideError = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }

}

// Everything the movie details screen needs, represented by one immutable value.
data class MovieDetailsUiState(
    val overviewState: ApiCallState = ApiCallState.IDLE,
    val parentalGuideState: ApiCallState = ApiCallState.IDLE,
    val overview: MovieOverViewResponse? = null,
    val images: MovieImagesResponse? = null,
    val parentalGuide: MovieParentalGuideResponse? = null,
    val overviewError: String = "",
    val parentalGuideError: String = ""
)

//States of an API call
enum class ApiCallState { LOADING, IDLE, ERROR, SUCCESS }
