package com.keeghan.movieinfo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeghan.movieinfo.viewModel.ApiCallState
import com.keeghan.movieinfo.viewModel.MovieDetailsViewModel

@Composable
fun PreviewScreen(
    movieId: String,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val pg by viewModel.pgResponse.observeAsState()


    LaunchedEffect(Unit) {
        viewModel.findOverView(movieId)
        viewModel.getParentalGuidance(movieId)
    }

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        Spacer(modifier = Modifier.weight(0.5f))

        when (uiState.value.overViewState) {
            ApiCallState.SUCCESS -> {
                if (pg?.parentalguide?.isNotEmpty() == true) {
                    ParentsGuideSection(parentalGuides = pg!!.parentalguide)
                }
            }

            ApiCallState.LOADING -> {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            ApiCallState.ERROR -> {

            }

            ApiCallState.IDLE -> {
                //Shows up on first Run...Do nothing
            }
        }
    }
}

