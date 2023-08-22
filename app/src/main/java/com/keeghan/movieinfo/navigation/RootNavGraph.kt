package com.keeghan.movieinfo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.keeghan.movieinfo.ui.screens.BottomBarScreen

/**
 * Top level Graph
 * */
@Composable
fun RootNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = MainGraph.ROOT,
        startDestination = MainGraph.BOTTOM_BAR_SCREEN
    ) {
        //Handle navigation here to provide consistency of graph
        composable(route = MainGraph.BOTTOM_BAR_SCREEN) {
            BottomBarScreen(
                onMovieCardClick = { id -> navController.navigate("${MainGraph.MOVIE_INFO_SCREEN}/$id") },
                onSettingsClick = { navController.navigate(SettingsGraph.SETTINGS) }
            )
        }

        movieInfoNavGraph(navController)
        settingsNavGraph(navController)
    }
}

object MainGraph {
    const val ROOT = "root_graph"
    const val BOTTOM_NAV_GRAPH = "bottom_nav"
    const val MOVIE_INFO = "movie-info_graph"
    const val MOVIE_INFO_SCREEN = "movie-info_screen"
    const val BOTTOM_BAR_SCREEN = "main_screen"
    const val CONTENT_ADVISORY = "content_advisory"
}

object SettingsGraph {
    const val SETTINGS = "settings"
    const val SETTINGS_SCREEN = "settings_screen"
}