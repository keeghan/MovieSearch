package com.keeghan.movieinfo.ui.screens

import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.keeghan.movieinfo.viewModel.ApiCallState
import com.keeghan.movieinfo.viewModel.MovieDetailsViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okio.ByteString.Companion.encodeUtf8

@Composable
fun PreviewScreen(
    navController: NavController = rememberNavController(),
    movieId: String,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val pg = uiState.parentalGuide


    LaunchedEffect(Unit) {
        //   viewModel.findOverView(movieId)
        viewModel.getParentalGuidance(movieId)
    }

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        Spacer(modifier = Modifier.weight(0.5f))


        if (pg?.parentalguide?.isNotEmpty() == true) {
            val pgString = Json.encodeToString(pg)
            Log.i("Preview", pgString.encodeUtf8().toString())
            ContentAdvisoryScreen(
                navController = rememberNavController(), pgString = pgString
            )

            ParentsGuideSection(parentalGuides = pg.parentalguide.orEmpty()) {
                //   navController.navigate("${Graph.CONTENT_ADVISORY}/$pgString")

            }
        }



        when (uiState.overviewState) {
            ApiCallState.SUCCESS -> {
//                if (pg?.parentalguide?.isNotEmpty() == true) {
//                    val pgString = Json.encodeToString(pg)
//                    Log.i("Preview", pgString)
//                    ContentAdvisoryScreen(
//                        navController = rememberNavController(),
//                        pgString = pgString
//                    )
//
//                    ParentsGuideSection(parentalGuides = pg!!.parentalguide) {
//                        //   navController.navigate("${Graph.CONTENT_ADVISORY}/$pgString")
//
//                    }
//                }
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

