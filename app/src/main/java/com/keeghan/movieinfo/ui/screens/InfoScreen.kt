package com.keeghan.movieinfo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.keeghan.movieinfo.utils.Space

@Composable
fun InfoScreen(
    navController: NavController,
    movieId: String,
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Space(side = 50.dp)
        Text(text = "InfoScreen")
        Text(text = movieId)
    }
}
