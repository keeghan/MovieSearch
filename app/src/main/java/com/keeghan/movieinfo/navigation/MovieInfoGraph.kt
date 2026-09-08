package com.keeghan.movieinfo.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.keeghan.movieinfo.ui.screens.ContentAdvisoryScreen
import com.keeghan.movieinfo.ui.screens.InfoScreen
import com.keeghan.movieinfo.viewModel.MovieDetailsViewModel

/**
 * Sub graph with destinations of inner screens.
 */
fun NavGraphBuilder.movieInfoNavGraph(navController: NavController) {
    navigation(
        route = MainGraph.MOVIE_INFO, startDestination = MainGraph.MOVIE_INFO_SCREEN
    ) {
        composable(
            route = "${MainGraph.MOVIE_INFO_SCREEN}/{movieId}", arguments = listOf(
                navArgument("movieId") {
                    type = NavType.StringType
                })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: return@composable

            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(MainGraph.BOTTOM_NAV_GRAPH)
            }

            val sharedViewModel: MovieDetailsViewModel = hiltViewModel(parentEntry)

            InfoScreen(
                navController = navController,
                movieId = movieId,
                viewModel = sharedViewModel,
                onContentAdvisoryClick = {
                    navController.navigate("${MainGraph.CONTENT_ADVISORY}/$movieId")
                })
        }

        composable(
            route = "${MainGraph.CONTENT_ADVISORY}/{movieId}", arguments = listOf(
                navArgument("movieId") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: return@composable

            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(MainGraph.BOTTOM_NAV_GRAPH)
            }

            val sharedViewModel: MovieDetailsViewModel = hiltViewModel(parentEntry)
            ContentAdvisoryScreen(movieId = movieId, viewModel = sharedViewModel)
        }
    }
}