package com.keeghan.movieinfo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.keeghan.movieinfo.ui.screens.ContentAdvisoryScreen
import com.keeghan.movieinfo.ui.screens.InfoScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
        ) { //navigate to movie details page with movie Id
            InfoScreen(
                navController, movieId = it.arguments?.getString("movieId")!!
            ) { //encode string object of type MovieParentalGuideResponse before sending
                    pgObjectString ->
                val encodeObject = URLEncoder.encode(pgObjectString, StandardCharsets.UTF_8.toString())
                navController.navigate("${MainGraph.CONTENT_ADVISORY}/$encodeObject")
            }
        }

        //Receive parentalGuidance as String
        composable(
            route = "${MainGraph.CONTENT_ADVISORY}/{pgString}",
            arguments = listOf(navArgument("pgString") { type = NavType.StringType })
        ) { //navigate to movie details page with movie Id
            ContentAdvisoryScreen(
                navController, pgString = it.arguments?.getString("pgString")!!
            )
        }
    }
}