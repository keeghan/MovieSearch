package com.keeghan.movieinfo.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.keeghan.movieinfo.R
import com.keeghan.movieinfo.ui.components.WatchListMovieCard
import com.keeghan.movieinfo.ui.theme.seed
import com.keeghan.movieinfo.utils.MoviePosters
import com.keeghan.movieinfo.utils.SpaceH
import com.keeghan.movieinfo.utils.SpaceW

val engagements = listOf("Ratings", "Lists", "Reviews")

@Composable
fun ProfileScreen(
    navController: NavController = rememberNavController(), onSettingsClick: () -> Unit
) {
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 55.dp)
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionBar { onSettingsClick() }
        //Engagement Sections
        LazyRow(
            contentPadding = PaddingValues(start = 10.dp, end = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(engagements.size) { HomeCard(engagementType = engagements[it]) { displayNotEnabledToast(context) } }
        }
        SpaceH(20.dp)
        ProfileSection(stringResource(R.string.watchlist),
            stringResource(R.string.watchlist_sub)
        ) { displayNotEnabledToast(context) }
        SpaceH(15.dp)
        ProfileSection(stringResource(R.string.recently_viewed),"") { displayNotEnabledToast(context) }
        SpaceH(15.dp)

        //Favorite People Section
        Card(Modifier.fillMaxWidth(), shape = RectangleShape) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 5.dp)
            ) {
                TitleCard(sectionName = stringResource(R.string.favorite_people)) { displayNotEnabledToast(context) }
                Text(
                    text = stringResource(R.string.add_favorite_actors),
                    modifier = Modifier.padding(end = 5.dp, top = 5.dp),
                    lineHeight = 16.sp
                )
                Button(
                    onClick = { displayNotEnabledToast(context) },
                    colors = ButtonDefaults.buttonColors(containerColor = seed),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 5.dp),
                    shape = RoundedCornerShape(5.dp)
                ) { Text(text = stringResource(R.string.add_to_favorite_people), style = MaterialTheme.typography.bodyLarge) }
                SpaceH(20.dp)
            }
        }
        SpaceH(20.dp)
        ProfileCard(stringResource(R.string.favourite_cinemas)) { displayNotEnabledToast(context) }
        ProfileCard(stringResource(R.string.check_ins)) { displayNotEnabledToast(context) }
        ProfileCard(stringResource(R.string.notifs)) { displayNotEnabledToast(context) }
        SpaceH(5.dp)
    }
}

fun displayNotEnabledToast(context: Context) {
    Toast.makeText(context, context.getString(R.string.feature_not_implemented), Toast.LENGTH_SHORT).show()
}


@Composable
fun ActionBar(
    onSettingsClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.AccountCircle, stringResource(R.string.profile), tint = seed)
        Text(text = "Mr. Man", modifier = Modifier.padding(start = 10.dp))
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onSettingsClick() }) {//Send button click upwards to RootNav to Settings
            Icon(Icons.Default.Settings, stringResource(R.string.settings))
        }
    }
    SpaceH(10.dp)
}

@Composable
fun HomeCard(
    engagementType: String,
    onFeatureClick: () -> Unit
) {
    val insideText = when (engagementType) {
        "Ratings" -> stringResource(R.string.rate_show)
        "Lists" -> stringResource(R.string.create_list)
        "Reviews" -> stringResource(R.string.no_reviews)
        else -> throw IllegalArgumentException()
    }
    Card(
        modifier = Modifier.width(180.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp, pressedElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = insideText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable { onFeatureClick() })
                }
            }
            SpaceH(15.dp)
            Text(text = engagementType)
            Text(text = "0")
        }
    }
}


@Composable
fun ProfileSection(
    sectionName: String,
    sub:String,
    onFeatureClick: () -> Unit
) {
    Card(
        Modifier.fillMaxWidth(),
        shape = RectangleShape,
    ) {
        Column(modifier = Modifier.padding(start = 15.dp)) {
            TitleCard(sectionName) { onFeatureClick() }
            if (sub.isNotBlank()){
                SpaceH(15.dp)
                Text(text = sub,modifier = Modifier.padding(end = 35.dp))
            }
            SpaceH(15.dp)
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                WatchListMovieCard(
                    ratings = "7.4",
                    title = "John Wick",
                    startYear = "2014",
                    endYear = "",
                    url = MoviePosters.johnWick,
                    runTime = "1h 41m"
                )
                if (sectionName == "Recently viewed") {
                    WatchListMovieCard(
                        ratings = "7.6",
                        title = "SEE",
                        startYear = "2019",
                        endYear = " – 2022",
                        url = MoviePosters.seeMovie,
                        runTime = "1h"
                    )
                }
                WatchListMovieCard(
                    ratings = "7.0",
                    title = "Extraction 2",
                    startYear = "2023",
                    endYear = "",
                    url = MoviePosters.Extraction,
                    runTime = "2h 2m"
                )
            }
        }
        SpaceH(5.dp)
    }
}


@Composable
fun TitleCard(
    sectionName: String,
    onFeatureClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .height(25.dp)
                .width(4.dp)
                .background(seed)
        )
        SpaceW(7.dp)
        Text(text = sectionName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.see_all), modifier = Modifier
                .padding(end = 10.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = MaterialTheme.colorScheme.primary),
                ) { onFeatureClick() },
            color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.bodyMedium
        )

    }
}

@Composable
fun ProfileCard(
    sectionName: String,
    onFeatureClick: () -> Unit
) {
    Card(shape = RectangleShape,
        modifier = Modifier.clickable { onFeatureClick() }) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            Text(text = sectionName, fontWeight = FontWeight.SemiBold)
            Text(text = "0")
        }
    }
    SpaceH(2.dp)
}

