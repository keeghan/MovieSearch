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
fun BottomNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    onMovieCardClick: (String) -> Unit
) {
    NavHost(
        navController = navController,
        route = Graph.BOTTOM_NAV_GRAPH,
        startDestination = BottomBarDestination.Search.route   //Home.route
    ) {
        composable(route = BottomBarDestination.Home.route) {
            HomeScreen(modifier)
        }
        composable(route = BottomBarDestination.Search.route) {
            SearchScreen(modifier, navController) { id ->
                onMovieCardClick(id)
            }
        }
        composable(route = BottomBarDestination.Video.route) {
            VideoScreen()
        }
        composable(route = BottomBarDestination.Profile.route) {
            ProfileScreen()
        }
    }
}

