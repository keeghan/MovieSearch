package com.keeghan.movieinfo.ui.screens

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.keeghan.movieinfo.navigation.BottomBarDestination
import com.keeghan.movieinfo.navigation.BottomNavGraph
import com.keeghan.movieinfo.navigation.MainGraph
import com.keeghan.movieinfo.navigation.SettingsGraph
import com.keeghan.movieinfo.ui.theme.seed


@Composable
fun BottomBarScreen(
    navController: NavHostController = rememberNavController(),
    // onMovieCardClick: (String) -> Unit,
    // onSettingsClick: () -> Unit
) {
    Scaffold(bottomBar = { BottomBar(navController) }) {
        BottomNavGraph(Modifier.padding(it),
            navController,
            onMovieCardClick = { id -> navController.navigate("${MainGraph.MOVIE_INFO_SCREEN}/$id") },
            onSettingsClick = { navController.navigate(SettingsGraph.SETTINGS) })
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarDestination.Home,
        BottomBarDestination.Search,
        BottomBarDestination.Video,
        BottomBarDestination.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    //Check to see if you are currently on one of the four main bottom bar screens before showing bar
    // val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    //  if (bottomBarDestination) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.height(55.dp)  //Remove to restore default size
    ) {
        screens.forEach { screen ->
            AddItem(screen = screen, currentDestination = currentDestination, navController = navController)
        }
    }
    //  }
}

/*
* Represents each items on the BottomNavBar
* */
@Composable
fun RowScope.AddItem(
    screen: BottomBarDestination,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    NavigationBarItem(
        // modifier = Modifier.scale(0.5f),
        // label = { Text(text = screen.title) },
        icon = {                            //fix inner destination bottom bar light icon
            val icon = if (isSelected) {
                screen.icon_focused
            } else {
                screen.icon
            }
            Icon(imageVector = icon, contentDescription = "Navigation Icon")
        },
        selected = isSelected,
        colors = NavigationBarItemDefaults.colors(
            unselectedIconColor = LocalContentColor.current.copy(alpha = 0.50f),
            selectedIconColor = MaterialTheme.colorScheme.surfaceTint,
            indicatorColor = MaterialTheme.colorScheme.background
        ),
        onClick = {
            navController.navigate(screen.route) {
                //When a bottomBar destination is clicked,
                //popUpTo, prevents building large stacks of destinations
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                //keep only one copy of each destination stack
                launchSingleTop = true
                // restore previous screens navigated from main bottom bar screens
                restoreState = true
            }
        },
        alwaysShowLabel = false
    )
}