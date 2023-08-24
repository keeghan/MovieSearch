package com.keeghan.movieinfo.ui.screens.settingsGroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeghan.movieinfo.ui.theme.seed

@Preview
@Composable
fun AccountScreen() {
    Column(Modifier.padding(2.dp)) {
        Text(text = "Account", color = seed)
        Card(shape = RectangleShape) {
            Column(Modifier.fillMaxWidth().padding(5.dp)) {
                Text(text = "User ID")
                Text(text = "mr_man-05168")
            }
        }
    }
}