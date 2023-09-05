package com.keeghan.movieinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.keeghan.movieinfo.navigation.RootNavGraph
import com.keeghan.movieinfo.ui.theme.MovieInfoTheme
import com.keeghan.movieinfo.viewModel.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val settingsViewModel: DataStoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dataStorePref by settingsViewModel.settingsPreferencesFlow.collectAsState(
                initial = DataStoreViewModel.SettingsPreferences(true)
            )

            MovieInfoTheme(darkTheme = dataStorePref.isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    RootNavGraph(navController = rememberNavController()) //Hello
                    // InfoScreen(navController = rememberNavController(), movieId = "tt0944947")
                    // InfoScreen(navController = rememberNavController(), movieId = "tt5861236")
                    // PreviewScreen(movieId = "tt0944947")
                    // PgItemPreview()
                    // NotificationScreen()
                }
            }
        }
    }
}