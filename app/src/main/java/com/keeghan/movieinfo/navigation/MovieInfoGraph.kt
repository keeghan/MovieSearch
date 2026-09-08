package com.keeghan.movieinfo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.keeghan.movieinfo.ui.screens.ContentAdvisoryScreen
import com.keeghan.movieinfo.ui.screens.InfoScreen

/**
 * Sub graph with destinations of inner screens
 * */
fun NavGraphBuilder.movieInfoNavGraph(navController: NavController) {
    navigation(
        route = MainGraph.MOVIE_INFO, startDestination = MainGraph.MOVIE_INFO_SCREEN
    ) {
        //Receive MovieId as String from [SearchScreen]
        composable(
            route = "${MainGraph.MOVIE_INFO_SCREEN}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
                ?: return@composable

            InfoScreen(
                navController = navController,
                movieId = movieId
            ) {
                navController.navigate("${MainGraph.CONTENT_ADVISORY}/$movieId")
            }
        }

        // Pass only the stable movie ID; the destination loads its own data.
        composable(
            route = "${MainGraph.CONTENT_ADVISORY}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
                ?: return@composable

            ContentAdvisoryScreen(movieId = movieId)
        }
    }
}
