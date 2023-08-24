package com.keeghan.movieinfo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.keeghan.movieinfo.ui.screens.SettingsScreen
import com.keeghan.movieinfo.ui.screens.VideoScreen
import com.keeghan.movieinfo.ui.screens.settingsGroup.AboutScreen
import com.keeghan.movieinfo.ui.screens.settingsGroup.AccountScreen
import com.keeghan.movieinfo.ui.screens.settingsGroup.DisplayScreen
import com.keeghan.movieinfo.ui.screens.settingsGroup.NotificationScreen
import com.keeghan.movieinfo.ui.screens.settingsGroup.PreferencesScreen
import com.keeghan.movieinfo.ui.screens.settingsGroup.StorageScreen
import com.keeghan.movieinfo.ui.screens.settingsGroup.VideoSettingsScreen

fun NavGraphBuilder.settingsNavGraph(
    navController: NavController
) {
    navigation(
        route = SettingsGraph.SETTINGS, startDestination = SettingsGraph.SETTINGS_SCREEN
    ) {
        composable(
            route = SettingsGraph.SETTINGS_SCREEN
        ) {
            SettingsScreen(
                onAccountClick = { navController.navigate(SettingsGraph.SETTINGS_ACCOUNT_SCREEN) },
                onDisplayClick = { navController.navigate(SettingsGraph.SETTINGS_DISPLAY_SCREEN) },
                onVideoClick = { navController.navigate(SettingsGraph.SETTINGS_VIDEO_SCREEN) },
                onPreferencesClick = { navController.navigate(SettingsGraph.SETTINGS_PREFERENCES_SCREEN) },
                onNotificationsClick = { navController.navigate(SettingsGraph.SETTINGS_NOTIFICATION_SCREEN) },
                onStorageClick = { navController.navigate(SettingsGraph.SETTINGS_STORAGE_SCREEN) },
                onAboutClick = { navController.navigate(SettingsGraph.SETTINGS_ABOUT_SCREEN) })
        }

        composable(route = SettingsGraph.SETTINGS_ACCOUNT_SCREEN) { AccountScreen() }

        composable(route = SettingsGraph.SETTINGS_DISPLAY_SCREEN) { DisplayScreen() }

        composable(route = SettingsGraph.SETTINGS_VIDEO_SCREEN) { VideoSettingsScreen() }

        composable(route = SettingsGraph.SETTINGS_PREFERENCES_SCREEN) { PreferencesScreen() }

        composable(route = SettingsGraph.SETTINGS_NOTIFICATION_SCREEN) { NotificationScreen() }

        composable(route = SettingsGraph.SETTINGS_STORAGE_SCREEN) { StorageScreen() }

        composable(route = SettingsGraph.SETTINGS_ABOUT_SCREEN) { AboutScreen() }
    }
}