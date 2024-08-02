package com.keeghan.movieinfo.ui.screens.settingsGroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.keeghan.movieinfo.R
import com.keeghan.movieinfo.ui.components.SettingsDropDownMenu
import com.keeghan.movieinfo.ui.components.SettingsScreenCard
import com.keeghan.movieinfo.ui.components.SettingsScreenTitle
import com.keeghan.movieinfo.utils.Constants

val networkOptions = listOf("WiFi and cellular", "WiFi only", "No auto-play")
val subsOptions = listOf("Always On", "On when muted", "Off")

@Composable
fun VideoSettingsScreen() {
    var networkOption by rememberSaveable { mutableIntStateOf(Constants.NetworkSettings.WIFI_CELLULAR) }
    var subOption by rememberSaveable { mutableIntStateOf(Constants.ThemeSettings.DARK_THEME) }

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = Constants.BOTTOM_BAR_PADDING)
    ) {
        SettingsScreenTitle(stringResource(R.string.video_c))
        SettingsScreenCard(
            title = stringResource(R.string.auto_play_video),
            subtitle = stringResource(R.string.auto_play_sub)
        ) {}

        //Network Select
        SettingsDropDownMenu(
            options = networkOptions,
            selectedOption = networkOption,
            onOptionSelect = {
                networkOption = when(it) {
                    networkOptions[0]-> {
                        Constants.NetworkSettings.WIFI_CELLULAR
                    }
                    networkOptions[1]-> {
                        Constants.NetworkSettings.WIFI_ONLY
                    }
                    else-> {
                        Constants.NetworkSettings.NO_AUTO_PLAY
                    }
                }
            }
        )

        Card(
            shape = RectangleShape,
        ) {
            Text(
                text = stringResource(R.string.subtitles_and_captions),
                Modifier
                    .padding(start = 15.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                    .fillMaxWidth()
            )
        }

        SettingsDropDownMenu(
            options = subsOptions,
            selectedOption = subOption,
            onOptionSelect = {
                subOption = when(it) {
                    subsOptions[0]-> {
                        Constants.SubTitleOptions.ALWAYS_ON
                    }
                    subsOptions[1]-> {
                        Constants.SubTitleOptions.ON_WHEN_MUTED
                    }
                    else-> {
                        Constants.SubTitleOptions.OFF
                    }
                }
            }
        )

        SettingsScreenCard(
            title = stringResource(R.string.captions_style),
            subtitle = stringResource(R.string.set_in_accessibility)
        ) {}
        HorizontalDivider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)
    }
}