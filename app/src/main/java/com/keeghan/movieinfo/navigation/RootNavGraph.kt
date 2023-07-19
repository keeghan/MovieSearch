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
        route = Graph.ROOT,
        startDestination = Graph.BOTTOM_BAR_SCREEN
    ) {
        composable(route = Graph.BOTTOM_BAR_SCREEN) {
            BottomBarScreen() { id ->
                navController.navigate("${Graph.MOVIE_INFO_SCREEN}/$id")
            }
        }

        movieInfoNavGraph(navController)
    }
}

object Graph {
    const val ROOT = "root_graph"

    const val BOTTOM_NAV_GRAPH = "bottom_nav"

    const val AUTHENTICATION = "auth_graph"
    const val MAIN = "main_graph"
    const val MOVIE_INFO = "movieinfo_graph"
    const val MOVIE_INFO_SCREEN = "movieinfo_screen"
    const val SETTINGS = "settings"
    const val MYADS = "myads"
    const val BOTTOM_BAR_SCREEN = "main_screen"
    const val CONTENT_ADVISORY = "content_advisory"
}