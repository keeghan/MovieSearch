package com.keeghan.movieinfo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

//Class Representing the BottomNavigation destinations
sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val icon_focused: ImageVector,
) {
    //for recipe homepage
    object Home : BottomBarScreen(
        route = "home",
        title = "home",
        icon = Icons.Outlined.Home,
        icon_focused = Icons.Filled.Home
    )

    //for recipe homepage
    object Search : BottomBarScreen(
        route = "search",
        title = "search",
        icon = Icons.Outlined.Search,
        icon_focused = Icons.Filled.Search
    )

    //for plan homepage
    object Video : BottomBarScreen(
        route = "video",
        title = "video",
        icon = Icons.Outlined.PlayArrow,
        icon_focused = Icons.Filled.PlayArrow
    )

    //for plan homepage
    object Profile : BottomBarScreen(
        route = "profile",
        title = "profile",
        icon = Icons.Outlined.Person,
        icon_focused = Icons.Filled.Person
    )
}
