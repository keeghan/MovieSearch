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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeghan.movieinfo.R
import com.keeghan.movieinfo.models.ParentalGuide
import com.keeghan.movieinfo.utils.SpaceH
import com.keeghan.movieinfo.utils.SpaceW
import com.keeghan.movieinfo.viewModel.ApiCallState
import com.keeghan.movieinfo.viewModel.MovieDetailsViewModel
import java.util.Locale


/**
 * Displays parental-guidance details loaded from a stable movie ID.
 * */
@Composable
fun ContentAdvisoryScreen(
    movieId: String,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.getParentalGuidance(movieId)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState.parentalGuideState) {
            ApiCallState.LOADING, ApiCallState.IDLE -> CircularProgressIndicator()

            ApiCallState.SUCCESS -> ContentAdvisoryList(
                parentalGuides = uiState.parentalGuide?.parentalguide.orEmpty()
            )

            ApiCallState.ERROR -> Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = uiState.parentalGuideError.ifBlank {
                        stringResource(R.string.unknown_error)
                    }
                )
                SpaceH(8.dp)
                Button(onClick = { viewModel.getParentalGuidance(movieId) }) {
                    Text(stringResource(R.string.reload))
                }
            }
        }
    }
}

@Composable
private fun ContentAdvisoryList(parentalGuides: List<ParentalGuide>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        parentalGuides.forEach { ContentAdvisoryCard(it) }
    }
}


@Composable
fun ContentAdvisoryCard(pgItem: ParentalGuide) {
    val votes = pgItem.severityVotes
    val status = votes?.status?.takeIf { it.isNotBlank() }
    val totalVotes = (votes?.mildVotes ?: 0) + (votes?.moderateVotes ?: 0) +
            (votes?.severeVotes ?: 0) + (votes?.noneVotes ?: 0)
    var isUserVoted by remember { mutableStateOf(false) }
    var btnLabelClicked by remember { mutableStateOf("") }

    val bgColor: Color = when (status) {
        "none" -> Green
        "mild" -> Color.Yellow
        "moderate" -> MovieColors.Orange
        "severe" -> Color.Red
        else -> Color.Transparent
    }
    val label: String = when (pgItem.label) {
        "nudity" -> stringResource(R.string.sex_and_nudity)
        "violence" -> stringResource(R.string.violence_and_gore)
        "profanity" -> stringResource(R.string.profanity)
        "alcohol" -> stringResource(R.string.alcohol_drugs_and_smoking)
        "frightening" -> stringResource(R.string.frightening_scenes)
        else -> return
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
            Card(
                modifier = Modifier
                    .height(45.dp)
                    .width(10.dp),
                shape = RoundedCornerShape(2.dp),
                colors = CardDefaults.cardColors(containerColor = bgColor),
                content = {})
            SpaceW(8.dp)
            Column {
                status?.let { severity ->
                    val localizedStatus = when (severity) {
                        "none" -> stringResource(R.string.severity_none)
                        "mild" -> stringResource(R.string.severity_mild)
                        "moderate" -> stringResource(R.string.severity_moderate)
                        "severe" -> stringResource(R.string.severity_severe)
                        else -> severity.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                        }
                    }
                    Text(text = localizedStatus)
                }
                Text(text = stringResource(R.string.based_on_user_votes, totalVotes))
            }
        }
        SpaceH(8.dp)
        //Hide pgComment if it is spoiler , otherwise show pgComment
        //null check on if item exits or not
        pgItem.items.orEmpty()
            .filter { !it.text.isNullOrBlank() }
            .take(3)
            .forEach { item ->
            var isTextSpoiler by remember { mutableStateOf(item.isSpoiler == true) }
            HorizontalDivider(thickness = Dp.Hairline, color = Color.Gray)
            AnimatedContent(targetState = isTextSpoiler, label = stringResource(R.string.spoiler_animation)) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 2.dp, end = 2.dp, bottom = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (it) {
                        Text(
                            text = stringResource(R.string.spoiler_warnings),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.weight(0.80f)
                        )
                        Icon(
                            Icons.Default.ArrowDropDown, contentDescription = stringResource(R.string.show_spoiler),
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .clickable { isTextSpoiler = false }
                        )
                    } else {
                        Text(
                            text = item.text ?: return@AnimatedContent,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(0.9f),
                            style = MaterialTheme.typography.labelLarge,
                        )
                        IconButton(onClick = { /*TODO: menu item clicked*/ }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = stringResource(R.string.more_options)
                            )
                        }
                    }
                }
            }
        } //End of Comments
        HorizontalDivider(thickness = Dp.Hairline, color = Color.Gray)
        Text(
            text = stringResource(R.string.user_votes, label),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
        )
        SpaceH(5.dp)

        /*Vote Buttons*/
        val voteButtons = listOf(
            stringResource(R.string.severity_none),
            stringResource(R.string.severity_mild),
            stringResource(R.string.severity_moderate),
            stringResource(R.string.severity_severe)
        )

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
                text = stringResource(R.string.vote_thanks),
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
        stringResource(R.string.severity_none) -> Green
        stringResource(R.string.severity_mild) -> Color.Yellow
        stringResource(R.string.severity_moderate) -> MovieColors.Orange
        stringResource(R.string.severity_severe) -> Color.Red
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
    ContentAdvisoryList(l)
}

