package com.keeghan.movieinfo.ui.screens.settingsGroup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.keeghan.movieinfo.R
import com.keeghan.movieinfo.ui.components.SettingsScreenCard
import com.keeghan.movieinfo.ui.components.SettingsScreenTitle
import com.keeghan.movieinfo.utils.Constants.APP_VERSION
import com.keeghan.movieinfo.utils.Constants.BOTTOM_BAR_PADDING

@Composable
fun AboutScreen() {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = BOTTOM_BAR_PADDING)
    ) {

        //Version
        SettingsScreenTitle(stringResource(R.string.version))
        Card(shape = RectangleShape) {
            Row(Modifier.fillMaxWidth()) {
                Text(text = "App version", modifier = Modifier.padding(15.dp))
                Spacer(modifier = Modifier.weight(1f))
                Text(text = APP_VERSION, modifier = Modifier.padding(15.dp))
            }
        }


        //Help
        SettingsScreenTitle(stringResource(R.string.help))
        Card(shape = RectangleShape) {
            SettingsScreenCard(title = "Need help?",
                subtitle = "Get your questions answered by MovieInfo staff and other users" +
                        "on the MovieInfo Community Forums.", onOptionSelect = {})
            SettingsButton(text = "Get support") { }
        }
        Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)


        //FeedBack
        SettingsScreenTitle(stringResource(R.string.feedback))
        Card(shape = RectangleShape) {
            SettingsScreenCard(title = "Write a review",
                subtitle = "Let others know what you think in the Google play store.", onOptionSelect = {})
            SettingsButton(text = "Write a review") { }
            Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)
            SettingsScreenCard(title = "Provide feedback",
                subtitle = "Let us know if you have an issue or an idea", onOptionSelect = {})
            SettingsButton(text = "Email us") { }
        }
        Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)


        //Legal
        SettingsScreenTitle(stringResource(R.string.legal))
        Card(shape = RectangleShape) {
            Row(Modifier.fillMaxWidth()) {
                Text(text = "Legal Information", modifier = Modifier.padding(15.dp))
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)
    }
}


@Composable
fun SettingsButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(start = 15.dp, bottom = 15.dp)
            .clickable { onClick() }
            .border(BorderStroke(1.dp, Color.DarkGray), shape = RoundedCornerShape(5.dp))
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(top = 20.dp, end = 15.dp, bottom = 20.dp, start = 15.dp)
        )
    }
}