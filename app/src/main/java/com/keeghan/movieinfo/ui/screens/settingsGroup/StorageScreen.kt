package com.keeghan.movieinfo.ui.screens.settingsGroup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.keeghan.movieinfo.R
import com.keeghan.movieinfo.ui.components.SettingsScreenCard
import com.keeghan.movieinfo.ui.theme.seed
import com.keeghan.movieinfo.utils.SpaceH

@Composable
fun StorageScreen() {
    Column(
        Modifier
            .padding(bottom = 16.dp, top = 10.dp, start = 15.dp)
            .fillMaxSize()
    ) {

        Text(text = stringResource(R.string.delete_cache))
        Text(
            text = "you can free up storage by deleting your cache.",
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 15.sp,
        )


        SpaceH(15.dp)
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .clickable {  }
                    .border(BorderStroke(1.dp, Color.DarkGray), shape = RoundedCornerShape(3.dp))
            ) {
                Text(
                    text = stringResource(R.string.delete_cache_c),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(top = 15.dp, end = 10.dp, bottom = 15.dp, start = 10.dp)
                )
            }
        }
    }
}
