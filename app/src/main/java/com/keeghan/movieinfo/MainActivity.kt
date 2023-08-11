package com.keeghan.movieinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.keeghan.movieinfo.navigation.RootNavGraph
import com.keeghan.movieinfo.ui.screens.PgItemPreview
import com.keeghan.movieinfo.ui.screens.PreviewScreen
import com.keeghan.movieinfo.ui.theme.MovieInfoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieInfoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                  //  RootNavGraph(navController = rememberNavController()) //Hello
                    // InfoScreen(navController = rememberNavController(), movieId = "tt0944947")
                    // InfoScreen(navController = rememberNavController(), movieId = "tt5861236")
                    PreviewScreen(movieId = "tt0944947")
                   // PgItemPreview()
                }
            }
        }
    }
}