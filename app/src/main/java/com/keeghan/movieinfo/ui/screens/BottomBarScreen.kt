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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.keeghan.movieinfo.navigation.BottomBarDestination
import com.keeghan.movieinfo.navigation.BottomNavGraph


@Composable
fun BottomBarScreen(
    navController: NavHostController = rememberNavController(),
    onMovieCardClick: (String) -> Unit,
    onSettingsClick: () -> Unit
) {
    Scaffold(bottomBar = { BottomBar(navController) }) {
        BottomNavGraph(Modifier.padding(it),
            navController,
            onMovieCardClick = { id -> onMovieCardClick(id) },
            onSettingsClick = { onSettingsClick() })
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

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar(
            modifier = Modifier.height(55.dp)  //Remove to restore default size
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen, currentDestination = currentDestination, navController = navController
                )
            }
        }
    }
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
    NavigationBarItem(
        // modifier = Modifier.scale(0.5f),
        // label = { Text(text = screen.title) },
        icon = {
            val icon = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                screen.icon_focused
            } else {
                screen.icon
            }
            Icon(
                imageVector = icon, contentDescription = "Navigation Icon"
            )
        }, selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        // unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        colors = NavigationBarItemDefaults.colors(
            unselectedIconColor = LocalContentColor.current.copy(alpha = 0.50f),
            selectedIconColor = MaterialTheme.colorScheme.secondary
        ), onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }, alwaysShowLabel = false
    )
}