package com.keeghan.movieinfo.ui.screens.settingsGroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.keeghan.movieinfo.R
import com.keeghan.movieinfo.ui.components.SettingsDropDownMenu
import com.keeghan.movieinfo.ui.components.SettingsScreenCard
import com.keeghan.movieinfo.ui.components.SettingsScreenTitle
import com.keeghan.movieinfo.utils.Constants.Companion

val networkOptions = listOf("WiFi and cellular", "WiFi only", "No auto-play")
val subsOptions = listOf("Always On", "On when muted", "Off")

@Preview
@Composable
fun VideoSettingsScreen() {
    var networkOption by remember { mutableIntStateOf(Companion.NetworkSettings.WIFI_CELLULAR) }
    var subOption by remember { mutableIntStateOf(Companion.ThemeSettings.DARK) }

    Column {
        SettingsScreenTitle(stringResource(R.string.video_c))
        SettingsScreenCard(
            isToggle = false,
            toggleState = false,
            title = "Auto-play video",
            subtitle = "Enable to automatically play video muted at the top of title and name pages"
        ){}

        //Network Select
        SettingsDropDownMenu(
            options = networkOptions,
            selectedOption = networkOption,
            onOptionSelect = {
                networkOption = when(it) {
                    networkOptions[0]-> { Companion.NetworkSettings.WIFI_CELLULAR }
                    networkOptions[1]-> {  Companion.NetworkSettings.WIFI_ONLY }
                    else-> {  Companion.NetworkSettings.NO_AUTO_PLAY }
                }
            }
        )

        Card(
            shape = RectangleShape,
        ) {
            Text(
                text = "Subtitles and captions",
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
                    subsOptions[0]-> { Companion.SubTitleOptions.ALWAYS_ON }
                    subsOptions[1]-> { Companion.SubTitleOptions.ON_WHEN_MUTED  }
                    else-> { Companion.SubTitleOptions.OFF }
                }
            }
        )

        SettingsScreenCard(
            isToggle = false,
            toggleState = false,
            title = "Captions style",
            subtitle = "Set in Accessibility"
        ){}
        Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)
    }
}