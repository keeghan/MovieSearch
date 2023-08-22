package com.keeghan.movieinfo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeghan.movieinfo.viewModel.SearchViewModel

@Composable
fun HomeScreen() {
    ScreenNotImplemented()
}

@Composable
fun ScreenNotImplemented() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sorry, only Search and You screens are implemented as of now",
            textAlign = TextAlign.Center)
    }
}