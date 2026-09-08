package com.keeghan.movieinfo.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeghan.movieinfo.viewModel.MovieDetailsViewModel

@Composable
fun PreviewScreen(
    movieId: String,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    ContentAdvisoryScreen(movieId = movieId, viewModel = viewModel)
}

