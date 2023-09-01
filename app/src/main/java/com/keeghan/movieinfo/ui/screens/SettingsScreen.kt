package com.keeghan.movieinfo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.keeghan.movieinfo.R
import com.keeghan.movieinfo.utils.Constants
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

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = Constants.BOTTOM_BAR_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SettingsItem(label = stringResource(R.string.account)) { onAccountClick() }
        SettingsItem(label = stringResource(R.string.display)) { onDisplayClick() }
        SettingsItem(label = stringResource(R.string.video)) { onVideoClick() }
        SettingsItem(label = stringResource(R.string.watch_preferences)) { onPreferencesClick() }
        SettingsItem(label = stringResource(R.string.notifs)) { onNotificationsClick() }
        SettingsItem(label = stringResource(R.string.storage)) { onStorageClick() }
        SettingsItem(label = stringResource(R.string.about)) { onAboutClick() }

        SpaceH(20.dp)

        OutlinedButton(
            onClick = { }, shape = RectangleShape
        ) {
            Text(text = stringResource(R.string.sign_out))
        }
    }
}

@Composable
fun SettingsItem(
    label: String, onClick: () -> Unit
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
    if (label != stringResource(R.string.about)) {
        Divider(thickness = Dp.Hairline)
    }
}