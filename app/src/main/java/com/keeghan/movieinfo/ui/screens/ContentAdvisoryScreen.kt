package com.keeghan.movieinfo.ui.screens

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.keeghan.movieinfo.models.MovieParentalGuideResponse
import com.keeghan.movieinfo.models.ParentalGuide
import com.keeghan.movieinfo.utils.SpaceH
import com.keeghan.movieinfo.utils.SpaceW
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.Locale


/**
 *Composable that displays a list of examples of plot points
 * that may violate parental guidance
 * @param navController  passed from navigation graph to be used for navigation
 * @param pgString  [MovieParentalGuideResponse] object encoded to string
 * with kotlinx Serializer and URLEncoder
 * */
@Composable
fun ContentAdvisoryScreen(
    navController: NavController,
    pgString: String
) {
    //decode MovieParentalGuideResponse string passed from InfoScreen  back to object
    val decodedObjectString = URLDecoder.decode(pgString, StandardCharsets.UTF_8.toString())
    //Convert MovieParentalGuideResponse string passed from InfoScreen  back to object
    val pg: MovieParentalGuideResponse = Json.decodeFromString(decodedObjectString)

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        pg.parentalguide.forEach { pgItem ->
            ContentAdvisoryCard(pgItem)
        }
    }
}


@Composable
fun ContentAdvisoryCard(pgItem: ParentalGuide) {
    val votes = pgItem.severityVotes
    val totalVotes = votes.mildVotes + votes.moderateVotes + votes.severeVotes + votes.noneVotes
    var isUserVoted by remember { mutableStateOf(false) }
    var btnLabelClicked by remember { mutableStateOf("") }

    val bgColor: Color = when (votes.status) {
        "none" -> Green
        "mild" -> Color.Yellow
        "moderate" -> MovieColors.Orange
        "severe" -> Color.Red
        else -> {
            Color.Transparent
        }
    }
    val label: String = when (pgItem.label) {
        "nudity" -> "Sex and nudity"
        "violence" -> "Violence and gore"
        "profanity" -> "Profanity"
        "alcohol" -> "Alcohol, drugs and smoking"
        "frightening" -> "Frightening and intense scenes"
        else -> {
            " "
        }
    }

    Card(

        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(start = 10.dp)
        )
        SpaceH(5.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)
        ) {
            Card(modifier = Modifier
                .height(45.dp)
                .width(10.dp),
                shape = RoundedCornerShape(2.dp),
                colors = CardDefaults.cardColors(containerColor = bgColor),
                content = {})
            SpaceW(8.dp)
            Column {
                Text(
                    text = votes.status.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    },
                )
                Text(text = "Based on $totalVotes user votes")
            }
        }
        SpaceH(8.dp)
        //Hide pgComment if it is spoiler , otherwise show pgComment
        //null check on if item exits or not
        pgItem.items?.take(3)?.forEach { item ->
            var isTextSpoiler by remember { mutableStateOf(item.isSpoiler) }
            Divider(
                thickness = Dp.Hairline, color = Color.Gray,
            )
            AnimatedContent(targetState = isTextSpoiler, label = "spoiler_animation") {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 2.dp, end = 2.dp, bottom = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (it) {
                        Text(
                            text = "WARNING: SPOILERS", style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.weight(0.80f)
                        )
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "show spoiler",
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .clickable { isTextSpoiler = false }
                        )
                    } else {
                        Text(
                            text = item.text,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(0.9f),
                            style = MaterialTheme.typography.labelLarge,
                        )
                        IconButton(onClick = { /*TODO: menu item clicked*/ }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "more")
                        }
                    }
                }
            }
        } //End of Comments
        Divider(
            thickness = Dp.Hairline, color = Color.Gray,
        )
        Text(
            text = "What is your vote for $label?",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
        )
        SpaceH(5.dp)

        /*Vote Buttons*/
        val voteButtons = listOf("None", "Mild", "Moderate", "Severe")

        //Create 4 button grid by using two loops
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (i in 0 until 2) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (j in 0 until 2) {    //inner loop for next 2 items
                        val btnLabel = voteButtons[i * 2 + j]
                        Box(Modifier.weight(1f)) {
                            VoteButton(
                                label = btnLabel, isVoted = isUserVoted, selectedLabel = btnLabelClicked
                            ) {
                                isUserVoted = if (btnLabelClicked == it) {
                                    !isUserVoted
                                } else {
                                    true
                                }
                                btnLabelClicked = it
                            }
                        }
                    }
                }
            }
        }


        SpaceH(4.dp)
        AnimatedVisibility(visible = isUserVoted) {//Animate When user Vote
            Text(
                text = "Thanks for your Vote",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        SpaceH(10.dp)
    }
    SpaceH(10.dp)
}


@Composable
fun VoteButton(
    label: String,
    selectedLabel: String,
    isVoted: Boolean = false,
    onClick: (String) -> Unit
) {
    val bgColor: Color = when (label) {
        "None" -> Green
        "Mild" -> Color.Yellow
        "Moderate" -> MovieColors.Orange
        "Severe" -> Color.Red
        else -> {
            Color.Transparent
        }
    }

    val isSelected = isVoted && label == selectedLabel
    AnimatedContent(targetState = isSelected, label = "voteButtonAnimation") {
        val borderStroke = if (it) {
            null
        } else {
            BorderStroke(1.dp, Color.Gray)
        }
        Card(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .clickable { onClick(label) },
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (it) bgColor else {
                    Color.Transparent
                }
            ),
            border = borderStroke
        ) {
            Text(
                text = label,
                modifier = Modifier
                    .padding(7.dp)
                    .align(Alignment.CenterHorizontally),
                color = if (it) Color.Black else {
                    MaterialTheme.colorScheme.onBackground
                }
            )
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PgItemPreview() {
    val items1 = ParentalGuide.Item(
        hasProfanity = false,
        id = "/title/tt0944947/parentalguide/pg0657789",
        isSpoiler = true,
        language = "eng",
        text = "Incest occurs between several leading characters: a brother and sister, resulting in 3 children; the same female character engages in sexual activities with her first cousin, as well. An aunt and a nephew, who are approximately the same age, engage in an affair before they know of their relationship."
    )

    val items2 = ParentalGuide.Item(
        hasProfanity = true,
        id = "\"/title/tt0944947/parentalguide/pg1097036",
        isSpoiler = true,
        language = "eng",
        text = "A fully nude woman walks through a city as a punishment in front of 1,000,000 people. She survives however."
    )

    val items3 = ParentalGuide.Item(
        hasProfanity = true,
        id = "\"/title/tt0944947/parentalguide/pg1097036",
        isSpoiler = false,
        language = "eng",
        text = "There is a scene where two fully naked women are being taught by a fully clothed man how to sexually satisfy another. It appears that one woman is fingering the other."
    )

    val itemList = listOf(items1, items2, items3)
    val label1 = "nudity"
    val severity1 = ParentalGuide.SeverityVotes(
        mildVotes = 185, moderateVotes = 391, noneVotes = 420, severeVotes = 7099, status = "severe"
    )

    val pguideItem = ParentalGuide(itemList, label1, severity1)
    val l = listOf(
        pguideItem, pguideItem.copy(label = "violence"), pguideItem.copy(label = "alcohol")
    )
    val pgResponse = MovieParentalGuideResponse(l)
    val str = Json.encodeToString(pgResponse)

    ContentAdvisoryScreen(rememberNavController(), str)
}

