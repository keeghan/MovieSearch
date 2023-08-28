package com.keeghan.movieinfo.ui.screens.settingsGroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeghan.movieinfo.R
import com.keeghan.movieinfo.ui.components.SettingsDropDownMenu
import com.keeghan.movieinfo.ui.components.SettingsScreenCard
import com.keeghan.movieinfo.ui.components.SettingsScreenTitle
import com.keeghan.movieinfo.utils.Constants

val streamingRegions = listOf("amazon.com", "amazon.co.uk", "amazon.do", "amazon.in")

@Composable
fun PreferencesScreen() {
    Column {
        var streamRegion by remember { mutableIntStateOf(Constants.Companion.StreamingOptions.COM) }


        //IN THEATERS
        SettingsScreenTitle(label = "IN THEATERS ")

        //LOCATION SETTINGS
        Card(shape = RectangleShape) {
            Row(Modifier.padding(start = 15.dp, top = 15.dp, bottom = 15.dp, end = 25.dp)) {
                Text(text = "Location Settings")
                Spacer(modifier = Modifier.weight(1f))
                Icon(painterResource(id = R.drawable.my_location_24), "location icon")
            }
        }

        //BUY
        SettingsScreenTitle(label = "BUY")
        SettingsScreenCard(
            isToggle = false,
            toggleState = false,
            title = "Amazon Store", subtitle = "Select a location for online purchase options"
        ){}

        SettingsDropDownMenu(
            options = streamingRegions,
            selectedOption = streamRegion,
            onOptionSelect = {
                streamRegion = when(it) {
                    streamingRegions[0]-> { Constants.Companion.StreamingOptions.COM}
                    streamingRegions[1]-> { Constants.Companion.StreamingOptions.CO_UK }
                    streamingRegions[2]-> { Constants.Companion.StreamingOptions.DO}
                    else-> { Constants.Companion.StreamingOptions.IN }
                }
            }
        )

        //STREAMING
        SettingsScreenTitle(label = "STREAMING")
        Card(shape = RectangleShape) {
            Row(Modifier.padding(start = 15.dp, top = 12.dp, bottom = 12.dp, end = 10.dp)) {
                Text(text = "Location Settings")
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "0")
            }
        }
    }
}