package com.keeghan.movieinfo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.keeghan.movieinfo.ui.screens.SettingsScreen

fun NavGraphBuilder.settingsNavGraph(navController: NavController) {
    navigation(
        route = SettingsGraph.SETTINGS,
        startDestination = SettingsGraph.SETTINGS_SCREEN
    ) {
        composable(
            route = SettingsGraph.SETTINGS_SCREEN
        ) {
            SettingsScreen()
        }
    }
}