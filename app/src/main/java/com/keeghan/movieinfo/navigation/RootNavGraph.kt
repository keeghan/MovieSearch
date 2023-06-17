package com.keeghan.movieinfo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.keeghan.movieinfo.ui.screens.MainScreen

@Composable
fun RootNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.MAIN_SCREEN
    ) {
        composable(route = Graph.MAIN_SCREEN) {
            MainScreen() { id ->
                navController.navigate("${Graph.MOVIE_INFO_SCREEN}/$id")
            }
        }

        movieInfoNavGraph(navController)
    }

}

object Graph {
    const val BOTTOM_NAV = "bottom_nav"
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val MAIN = "main_graph"
    const val MOVIE_INFO = "movieinfo_graph"
    const val MOVIE_INFO_SCREEN = "movieinfo_screen"
    const val SETTINGS = "settings"
    const val MYADS = "myads"
    const val MAIN_SCREEN = "myads"
}