package com.keeghan.movieinfo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.keeghan.movieinfo.ui.screens.InfoScreen

fun NavGraphBuilder.movieInfoNavGraph(navController: NavController) {
    navigation(
        route = Graph.MOVIE_INFO,
        startDestination = Graph.MOVIE_INFO_SCREEN
    ) {
        composable(
            route = "${Graph.MOVIE_INFO_SCREEN}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
        ) {
            InfoScreen(navController, movieId = it.arguments?.getString("movieId")!!)
        }
    }
}
