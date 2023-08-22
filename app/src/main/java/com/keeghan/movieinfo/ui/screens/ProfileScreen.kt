package com.keeghan.movieinfo.ui.screens

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.keeghan.movieinfo.ui.components.WatchListMovieCard
import com.keeghan.movieinfo.ui.theme.seed
import com.keeghan.movieinfo.utils.MoviePosters
import com.keeghan.movieinfo.utils.SpaceH
import com.keeghan.movieinfo.utils.SpaceW

val engagements = listOf("Ratings", "Lists", "Reviews")

@Composable
fun ProfileScreen(
    navController: NavController = rememberNavController(),
    onSettingsClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 55.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionBar { onSettingsClick() }
        LazyRow(
            contentPadding = PaddingValues(start = 15.dp, end = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(engagements.size) {
                HomeCard(engagementType = engagements[it])
            }
        }
        SpaceH(side = 20.dp)
        ProfileSection("WatchList")
        SpaceH(side = 10.dp)
        ProfileSection("Recently viewed")
    }
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
        Icon(Icons.Default.AccountCircle, "Profile", tint = seed)
        Text(text = "Mr. Man", modifier = Modifier.padding(start = 10.dp))
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onSettingsClick() }) {//Send button click upwards to RootNav to Settings
            Icon(Icons.Default.Settings, "settings")
        }
    }
    SpaceH(10.dp)
}


@Composable
fun HomeCard(engagementType: String) {
    val insideText = when (engagementType) {
        "Ratings" -> "Rate a show or a movie"
        "Lists" -> "Create a list"
        "Reviews" -> "No Reviews"
        else -> throw IllegalArgumentException()
    }
    Card(
        modifier = Modifier.width(180.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp, pressedElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)) {
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
                            .clickable { }) //not Implemented }
                }
            }
            SpaceH(side = 15.dp)
            Text(text = engagementType)
            Text(text = "0")
        }
    }
}


@Composable
fun ProfileSection(
    sectionName: String
) {
    Column(modifier = Modifier.padding(start = 15.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .height(25.dp)
                        .width(5.dp)
                        .background(seed)
                )
                SpaceW(side = 7.dp)
                Text(
                    text = sectionName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold
                )
            }
            Text(text = "SEE ALL", modifier = Modifier
                .padding(end = 10.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = MaterialTheme.colorScheme.primary)
                ) { })
        }
        SpaceH(side = 15.dp)
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            WatchListMovieCard(
                ratings = "7.4",
                title = "John Wick",
                startYear = "2014",
                endYear = "",
                url = MoviePosters.johnWick,
                runTime = "1h 41m"
            )
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
}
