package com.keeghan.movieinfo.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.keeghan.movieinfo.ui.theme.seed


@Composable
fun SettingsScreenTitle(label: String) {
    Text(
        text = label, color = seed,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 10.dp)
    )
    Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)
}
