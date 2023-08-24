package com.keeghan.movieinfo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.keeghan.movieinfo.utils.SpaceH

@Composable
fun SettingsScreen(
    onAccountClick: () -> Unit,
    onDisplayClick: () -> Unit,
    onVideoClick: () -> Unit,
    onPreferencesClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onStorageClick: () -> Unit,
    onAboutClick: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SettingsItem(label = "Account") { onAccountClick() }
        SettingsItem(label = "Display") { onDisplayClick() }
        SettingsItem(label = "Video") { onVideoClick() }
        SettingsItem(label = "Watch preferences") { onPreferencesClick() }
        SettingsItem(label = "Notifications") { onNotificationsClick() }
        SettingsItem(label = "Storage") { onStorageClick() }
        SettingsItem(label = "About") { onAboutClick() }

        SpaceH(20.dp)

        OutlinedButton(
            onClick = { },
            shape = RectangleShape
        ) {
            Text(text = "SIGN OUT")
        }
    }
}

@Composable
fun SettingsItem(
    label: String,
    onClick: () -> Unit
) {
    Card(shape = RectangleShape, modifier = Modifier.clickable {
        onClick()
    }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = label, modifier = Modifier.padding(top = 2.dp, end = 2.dp))
        }
    }
    if (label != "About") {
        Divider(thickness = Dp.Hairline)

    }
}