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
import androidx.annotation.StringRes
import com.keeghan.movieinfo.R

//Class Representing the BottomNavigation destinations
sealed class BottomBarDestination(
    val route: String,
    @StringRes val titleRes: Int,
    val icon: ImageVector,
    val icon_focused: ImageVector,
) {
    //for recipe homepage
    object Home : BottomBarDestination(
        route = "home",
        titleRes = R.string.home,
        icon = Icons.Outlined.Home,
        icon_focused = Icons.Filled.Home
    )

    //for recipe homepage
    object Search : BottomBarDestination(
        route = "search",
        titleRes = R.string.search_tab,
        icon = Icons.Outlined.Search,
        icon_focused = Icons.Filled.Search
    )

    //for plan homepage
    object Video : BottomBarDestination(
        route = "video",
        titleRes = R.string.video_tab,
        icon = Icons.Outlined.PlayArrow,
        icon_focused = Icons.Filled.PlayArrow
    )

    //for plan homepage
    object Profile : BottomBarDestination(
        route = "profile",
        titleRes = R.string.profile_tab,
        icon = Icons.Outlined.Person,
        icon_focused = Icons.Filled.Person
    )
}
