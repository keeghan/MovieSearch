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


/**  Navigation Graphs for screens that appear
 * in bottom navigation, used in
 * */
@Composable
fun BottomNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    onMovieCardClick: (String) -> Unit,
    onSettingsClick: () -> Unit
) {
    NavHost(
        navController = navController,
        route = MainGraph.BOTTOM_NAV_GRAPH,
        startDestination = BottomBarDestination.Profile.route   //Home.route
    ) {
        composable(route = BottomBarDestination.Home.route) {
            HomeScreen() //Not Implemented
        }
        composable(route = BottomBarDestination.Search.route) {
            SearchScreen(modifier, navController) { id ->
                onMovieCardClick(id)
            }
        }
        composable(route = BottomBarDestination.Video.route) {
            VideoScreen() //Not Implemented
        }
        composable(route = BottomBarDestination.Profile.route) {
            ProfileScreen(navController) { onSettingsClick() }
        }

        movieInfoNavGraph(navController)

        settingsNavGraph(navController)
    }
}

