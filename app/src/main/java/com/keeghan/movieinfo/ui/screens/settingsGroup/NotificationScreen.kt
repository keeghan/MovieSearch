package com.keeghan.movieinfo.ui.screens.settingsGroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.keeghan.movieinfo.R
import com.keeghan.movieinfo.ui.components.SettingsScreenCard
import com.keeghan.movieinfo.utils.Constants

@Composable
fun NotificationScreen() {
    var isTipsOn by rememberSaveable { mutableStateOf(false) }
    var isTrailerOn by rememberSaveable { mutableStateOf(false) }
    var inTheaters by rememberSaveable { mutableStateOf(false) }
    var isRecommendationsOn by rememberSaveable { mutableStateOf(false) }
    var isNowStreamingOn by rememberSaveable { mutableStateOf(false) }
    var isTrendingOn by rememberSaveable { mutableStateOf(false) }
    var isNewsOn by rememberSaveable { mutableStateOf(false) }
    var isTonightOn by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = Constants.BOTTOM_BAR_PADDING)
    ) {

        //Tips and Tricks
        SettingsScreenCard(
            toggleState = isTipsOn,
            title = stringResource(R.string.tips_and_tricks),
            subtitle = stringResource(R.string.get_most_out)
        ) { isTipsOn = !isTipsOn }
        Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)

        //Trailers
        SettingsScreenCard(
            toggleState = isTrailerOn,
            title = stringResource(R.string.trailers),
            subtitle = stringResource(R.string.get_latest_trailers)
        ) { isTrailerOn = !isTrailerOn }
        Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)

        //In Theaters
        SettingsScreenCard(
            toggleState = inTheaters,
            title = stringResource(R.string.in_theaters),
            subtitle = stringResource(R.string.get_updates)
        ) { inTheaters = !inTheaters }
        Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)

        //Recommendations
        SettingsScreenCard(
            toggleState = isRecommendationsOn,
            title = stringResource(R.string.recommendations),
            subtitle = stringResource(R.string.personalized_recommendations)
        ) { isRecommendationsOn = !isRecommendationsOn }
        Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)

        //Now Streaming
        SettingsScreenCard(
            toggleState = isNowStreamingOn,
            title = stringResource(R.string.now_streaming),
            subtitle = stringResource(R.string.updates__watchlist)
        ) { isNowStreamingOn = !isNowStreamingOn }
        Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)

        //Trending Now
        SettingsScreenCard(
            toggleState = isTrendingOn,
            title = stringResource(R.string.trending_now),
            subtitle = stringResource(R.string.dive_in_to_trending)
        ) { isTrendingOn = !isTrendingOn }
        Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)

        //News
        SettingsScreenCard(
            toggleState = isNewsOn,
            title = stringResource(R.string.news),
            subtitle = stringResource(R.string.stay_up_to_date)
        ) { isNewsOn = !isNewsOn }
        Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)

        //On Tonight
        SettingsScreenCard(
            toggleState = isTonightOn,
            title = stringResource(R.string.on_tonight),
            subtitle = stringResource(R.string.updates_watchlist_tonight)
        ) { isTonightOn = !isTonightOn }
        Divider(Modifier.fillMaxWidth(), Dp.Hairline, color = Color.DarkGray)
    }
}