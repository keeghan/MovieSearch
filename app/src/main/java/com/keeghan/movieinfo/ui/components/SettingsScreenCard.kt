package com.keeghan.movieinfo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.keeghan.movieinfo.R
import com.keeghan.movieinfo.ui.theme.seed
import com.keeghan.movieinfo.utils.SpaceH


/** Settings Options for various setting screen, can come with a toggle or not
 * @param isToggle is boolean that determines if card has a toggle
 * @param toggleState is state of the toggle, it is receive from higher level composable
 * @param title is title of the card
 * @param subtitle is a brief statement on the behaviour of the option
 * that is handled by calling composable
 * @param onOptionSelect is a hoisted method that changes the toggleState value in it parent composable
 */
@Composable
fun SettingsScreenCard(
    isToggle: Boolean,
    toggleState: Boolean,
    title: String,
    subtitle: String,
    onOptionSelect: (String) -> Unit
) {
    Card(
        shape = RectangleShape,
        modifier = Modifier.clickable { if (!isToggle) onOptionSelect(title) }  //if there's a toggle remove onclick status from whole card
    ) {
        Row(
            Modifier
                .padding(start = 15.dp, top = 12.dp, bottom = 12.dp, end = 7.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column (Modifier.weight(0.2f).padding(end = 30.dp)){
                Text(text = title)
                SpaceH(5.dp)
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 15.sp,
                    color = Color.Gray
                )
            }
            if (isToggle) {
                Switch(
                    modifier = Modifier.scale(0.7f),
                    //   modifier = Modifier.size(15.dp),
                    checked = toggleState,
                    onCheckedChange = { onOptionSelect(title) },  //if there's a toggle hoist Switch state instead of card onclick
                    colors = SwitchDefaults.colors(
                        checkedIconColor = seed,
                        checkedTrackColor = seed
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun p() {
    SettingsScreenCard(
        isToggle = true,
        toggleState = true,
        title = stringResource(R.string.on_tonight),
        subtitle = stringResource(R.string.updates_watchlist_tonight),
        onOptionSelect = {})
}