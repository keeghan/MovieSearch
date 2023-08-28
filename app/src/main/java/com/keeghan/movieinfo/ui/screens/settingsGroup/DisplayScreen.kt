package com.keeghan.movieinfo.ui.screens.settingsGroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.keeghan.movieinfo.R
import com.keeghan.movieinfo.ui.components.SettingsDropDownMenu
import com.keeghan.movieinfo.ui.components.SettingsScreenCard
import com.keeghan.movieinfo.ui.components.SettingsScreenTitle
import com.keeghan.movieinfo.utils.Constants.Companion
import com.keeghan.movieinfo.utils.SpaceH

//List for Local Options
val themes = listOf("Dark", "Light")
val regionLanguage = listOf("(United Kingdom)", "(United States)")

@Composable
fun DisplayScreen() {
    var isDarkTheme by remember { mutableIntStateOf(Companion.ThemeSettings.DARK) }
    var isRegionUK by remember { mutableIntStateOf(Companion.RegionSettings.UK) }
    var isTipsOn by remember { mutableStateOf(true) }
    var showInLocalLanguage by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column {
        SettingsScreenTitle(stringResource(R.string.appearance))

        //App Theme
        SettingsScreenCard(
            false,
            toggleState = false,
            title = stringResource(R.string.app_theme),
            subtitle = stringResource(R.string.theme_settings_sub)
        ) {}

        //Theme Select
        SettingsDropDownMenu(
            options = themes,
            selectedOption = isDarkTheme
        ) {  //Todo: Light mode or dark mode
            isDarkTheme = if (it == "Dark") Companion.ThemeSettings.DARK else {
                Companion.ThemeSettings.LIGHT
            }
        }

        //Localization
        SettingsScreenTitle(label = stringResource(R.string.localization))
        SettingsScreenCard(
            isToggle = false,
            false,
            title = stringResource(R.string.app_language),
            subtitle = ""
        ) {}
        Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)

        SettingsScreenCard(  //Todo: Implement Theme Switch
            isToggle = false,
            toggleState = false,
            title = stringResource(R.string.region),
            subtitle = stringResource(R.string.region_settings_sub)
        ) {}

        //Region Language
        SettingsScreenCard(
            isToggle = true,
            toggleState = showInLocalLanguage,
            title = stringResource(R.string.title_option),
            subtitle = stringResource(R.string.language_sub)
        ) { showInLocalLanguage = !showInLocalLanguage }
        SettingsDropDownMenu(
            options = regionLanguage,
            selectedOption = isRegionUK
        ) {  //Todo: Light mode or dark mode
            isRegionUK = if (it == "(United Kingdom)") Companion.RegionSettings.UK else {
                Companion.RegionSettings.US
            }
        }

        //Help
        SettingsScreenTitle(stringResource(R.string.help))
        SettingsScreenCard(
            isToggle = true,
            toggleState = isTipsOn,
            title = stringResource(R.string.tips),
            subtitle = stringResource(R.string.tips_sub)
        ) { isTipsOn = !isTipsOn }
        Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)
    }
}

