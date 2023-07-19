package com.keeghan.movieinfo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keeghan.movieinfo.models.MovieImagesResponse
import com.keeghan.movieinfo.models.MovieOverViewResponse
import com.keeghan.movieinfo.models.MovieParentalGuideResponse
import com.keeghan.movieinfo.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    @Named("mainRepository") private val repository: EpisodeRepository,
    @Named("ioDispatcher") private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UIState(ApiCallState.IDLE, ApiCallState.IDLE, "", ""))
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _movieOverViewResponse = MutableLiveData<MovieOverViewResponse>()
    val movieOverViewResponse: LiveData<MovieOverViewResponse> = _movieOverViewResponse

    private val _movieImagesResponse = MutableLiveData<MovieImagesResponse>()
    val movieImagesResponse: LiveData<MovieImagesResponse> = _movieImagesResponse

    private val _pgResponse = MutableLiveData<MovieParentalGuideResponse>()
    val pgResponse: LiveData<MovieParentalGuideResponse> = _pgResponse


    //Get overview and movie images
    fun findOverView(title: String) {
        // _uiState.value = InfoScreenUiState.LOADING
        _uiState.update { it.copy(overViewState = ApiCallState.LOADING) }
        viewModelScope.launch {
            try {
                val response = repository.findOverView(title)
                val imagesResponse = repository.getImages(title)
                if (response.isSuccessful && imagesResponse.isSuccessful) {
                    _movieOverViewResponse.postValue(response.body())
                    _movieImagesResponse.postValue(imagesResponse.body())
                    _uiState.update { it.copy(overViewState = ApiCallState.SUCCESS) }
                } else {
                    if (!response.isSuccessful) {
                        _uiState.update { it.copy(titleApiError = response.message()) }
                    } else {
                        _uiState.update { it.copy(titleApiError = imagesResponse.message()) }
                    }
                    _uiState.update { it.copy(overViewState = ApiCallState.ERROR) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(titleApiError = e.message.toString()) }
                _uiState.update { it.copy(overViewState = ApiCallState.ERROR) }
            }
        }
    }

    /*
    * Get parental guidance
    * */
    fun getParentalGuidance(title: String) {
        _uiState.update { it.copy(pgState = ApiCallState.LOADING) }
        viewModelScope.launch {
            try {
                val response = repository.getParentalGuide(title)
                if (response.isSuccessful) {
                    _pgResponse.postValue(response.body())
                    _uiState.update { it.copy(pgState = ApiCallState.SUCCESS) }
                } else {
                    _uiState.update { it.copy(pgError = response.message()) }
                    _uiState.update { it.copy(pgState = ApiCallState.ERROR) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(pgError = e.message.toString()) }
                _uiState.update { it.copy(pgState = ApiCallState.ERROR) }
            }
        }
    }

}

//Keep track of state of Api calls
data class UIState(
    val overViewState: ApiCallState,
    val pgState: ApiCallState,
    val pgError: String,
    val titleApiError: String
)

//States of an API call
enum class ApiCallState { LOADING, IDLE, ERROR, SUCCESS }
