package com.keeghan.movieinfo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.keeghan.movieinfo.ui.screens.HomeScreen
import com.keeghan.movieinfo.ui.screens.ProfileScreen
import com.keeghan.movieinfo.ui.screens.SearchScreen
import com.keeghan.movieinfo.ui.screens.VideoScreen

@Composable
fun MainNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    onMovieCardClick: (String) -> Unit
) {
    NavHost(
        navController = navController,
        route = Graph.BOTTOM_NAV,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(modifier)
        }
        composable(route = BottomBarScreen.Search.route) {
            SearchScreen(modifier, navController) { id ->
                onMovieCardClick(id)
            }
        }
        composable(route = BottomBarScreen.Video.route) {
            VideoScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
    }
}

