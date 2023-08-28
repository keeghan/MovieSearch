package com.keeghan.movieinfo.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.keeghan.movieinfo.R
import com.keeghan.movieinfo.models.MovieImagesResponse
import com.keeghan.movieinfo.models.MovieOverViewResponse
import com.keeghan.movieinfo.models.ParentalGuide
import com.keeghan.movieinfo.ui.components.rememberStarRate
import com.keeghan.movieinfo.utils.MovieImageProvider
import com.keeghan.movieinfo.utils.MutableRatingStar
import com.keeghan.movieinfo.utils.SpaceH
import com.keeghan.movieinfo.utils.SpaceW
import com.keeghan.movieinfo.viewModel.ApiCallState
import com.keeghan.movieinfo.viewModel.MovieDetailsViewModel
import com.touchlane.gridpad.GridPad
import com.touchlane.gridpad.GridPadCellSize
import com.touchlane.gridpad.GridPadCells
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * A composable that is used to display an the details of a show
 * @param movieId  is a string received from the NavBackStackEntry use to make calls to to the [viewModel]
 * @param navController passed from when InfoScreen is called for navigation
 * @param viewModel makes calls with [movieId]
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InfoScreen(
    navController: NavController,
    movieId: String,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    onContentAdvisoryClick: (String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    val movieOverView by viewModel.movieOverViewResponse.observeAsState()
    val movieImages by viewModel.movieImagesResponse.observeAsState()
    val moviePgScores by viewModel.pgResponse.observeAsState()

    //remove large images to save data and prevent Canvas errors
    val images = movieImages?.images?.filter { it.width < 2000 && it.height <= 2000 }

    //Make Api calls when composable is launched
    LaunchedEffect(Unit) {
        viewModel.findOverView(movieId)
        viewModel.getParentalGuidance(movieId)
    }

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Use appropriate action on according to UiState
        when (uiState.value.overViewState) {
            ApiCallState.SUCCESS -> {
                //Title
                TitleScreen(overview = movieOverView!!)
                //Carousel
                Spacer(modifier = Modifier.height(3.dp))
                if (images.isNullOrEmpty()) {
                    MovieImageProvider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(
                                    constraints.copy(
                                        maxWidth = constraints.maxWidth + 20.dp.roundToPx(), //add the end padding 16.dp
                                    )
                                )
                                layout(placeable.width, placeable.height) {
                                    placeable.place(0, 0)
                                }
                            }, contentScale = ContentScale.FillHeight
                    )
                } else {
                    ImageSlider(images = images)
                }
                Spacer(modifier = Modifier.height(5.dp))
                //Plot
                PlotSection(
                    movieOverView!!.title?.image,
                    movieOverView!!.genres,
                    plot = movieOverView?.plotOutline?.text
                )
                LongDivider()
                //Ratings
                RatingSection(
                    userRaters = movieOverView!!.ratings?.ratingCount,
                    rating = movieOverView!!.ratings?.rating,
                    metaScore = 90,  //TODO: Api Limitation
                    metaCriticsNum = 80 //TODO: Api Limitation
                )
                Spacer(modifier = Modifier.height(5.dp))
                NotificationSection()

                /* Ratings Sections: check that ratings have successfully loaded and display Ratings Section*/
                when (uiState.value.pgState) {
                    ApiCallState.SUCCESS -> {
                        SpaceH(side = 20.dp)
                        if (moviePgScores?.parentalguide?.isNotEmpty() == true) {
                            //turn pgObject to string to pass as nav argument
                            val pgString = Json.encodeToString(moviePgScores)

                            ParentsGuideSection(
                                parentalGuides = moviePgScores!!.parentalguide
                            ) { onContentAdvisoryClick(pgString) }   //pass parentalGuidance objectString upwards
                        }
                    }

                    ApiCallState.LOADING -> {
                        Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    else -> {} //error handling and idle state done in outer calls
                }
            }

            ApiCallState.LOADING -> {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            ApiCallState.ERROR -> {
                val errorMessage = uiState.value.titleApiError.ifBlank { stringResource(R.string.unknown_error) }
                Toast.makeText(
                    LocalContext.current,
                    uiState.value.titleApiError,
                    Toast.LENGTH_SHORT
                ).show()

                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = { viewModel.findOverView(movieId) }) {
                        Text(text = stringResource(R.string.reload))
                    }
                }
            }

            ApiCallState.IDLE -> {
                //Shows up on first Run...Do nothing
            }
        }
    }

}  //End of Main Composable


/**
 * Composable that shows the tile of show/Movie
 * Displays main title,dates, Pg ratings etc..
 * @param overview represents a the response body from an Api
 * */
@Composable
fun TitleScreen(overview: MovieOverViewResponse) {
    val isTvSeries = (overview.title?.titleType ?: "") == "tvSeries"
    val isMovie = (overview.title?.titleType ?: "") == "movie"
    val title = if (overview.title?.title == null) {
        ""
    } else {
        overview.title.title
    }

    val textSize = when (title.length) {
        in 0..10 -> MaterialTheme.typography.displayMedium
        in 11..20 -> MaterialTheme.typography.displaySmall
        else -> MaterialTheme.typography.titleLarge
    }

    Column(
        Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = textSize
        )
        Row {//Texts
            Text(
                text = (overview.title?.titleType ?: ""),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(end = 10.dp)
            )
            //Display "start - End year" if series otherwise just date
            if (isTvSeries) {
                val time = "${overview.title?.seriesStartYear} - ${overview.title?.seriesEndYear}"
                TitleText(text = time)
            } else if (isMovie) {
                TitleText(text = overview.title?.year.toString())
                if (overview.certificates != null) {
                    TitleText(text = overview.certificates.uS[0].certificate)
                }
            }
            TitleText(text = timeToStr(overview.title?.runningTimeInMinutes ?: 0))
        }

        //Display "Episode Guide" only if input is from a tvseries
        if (isTvSeries) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "EPISODE GUIDE",
                    Modifier
                        .padding(start = 0.dp, end = 10.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = MaterialTheme.colorScheme.primary)
                        ) {})
                TitleText(
                    text = overview.title?.numberOfEpisodes.toString() + " episodes"
                )
            }
        }
    }
}


@Composable
fun TitleText(text: String) {
    Text(text = text, modifier = Modifier.padding(end = 10.dp))
}

/**
 * A composable that uses a @see [HorizontalPager] to display images
 * @param images represents Images from a [MovieImagesResponse]
 * to be displayed in a pager
 * @param pagerState to be applied to the [HorizontalPager] to autoscroll
 * @param autoScrollDuration reps the time till [HorizontalPager] used in
 * [ImageSlider] autoscrolls
 * */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(
    images: List<MovieImagesResponse.Image>,
    pagerState: PagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = images::size
    ),
    autoScrollDuration: Long = 6000L
) {
    //create pagerState with auto scroll
    val pageCount = images.size
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    //If user hasn't dragged pager in 6 seconds, auto scroll
    if (isDragged.not()) {
        with(pagerState) {
            var currentPageKey by remember { mutableIntStateOf(0) }
            LaunchedEffect(key1 = currentPageKey) {
                launch {
                    delay(timeMillis = autoScrollDuration)
                    val nextPage = (currentPage + 1).mod(pageCount)
                    animateScrollToPage(page = nextPage)
                    currentPageKey = nextPage
                }
            }
        }
    }

    HorizontalPager(
        modifier = Modifier.layout { measurable, constraints ->
            val placeable = measurable.measure(
                constraints.copy(
                    maxWidth = constraints.maxWidth + 20.dp.roundToPx(), //add the end padding 16.dp
                )
            )
            layout(placeable.width, placeable.height) {
                placeable.place(0, 0)
            }
        },
        //  pageCount = images.size,
        state = pagerState
    ) {
        val image = images[it]
        val isImageTooLarge = image.height > 2500 || image.width > 2500
        Box(contentAlignment = Alignment.BottomStart) {
            //do not render large images
            MovieImageProvider(
                url = if (isImageTooLarge) "null" else image.url,
                contentDescription = if (isImageTooLarge) stringResource(id = R.string.image_large) else image.caption,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Text(text = if (isImageTooLarge) stringResource(id = R.string.image_large)  else image.caption)
        }
    }
}

//Plot section with image, genre and plot
@Composable
private fun PlotSection(
    image: MovieOverViewResponse.Title.Image?,
    genres: List<String>?,
    plot: String? = stringResource(id = R.string.no_plot)
) {
    Column(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Row {
            var isImageTooLarge = false
            if (image != null) {
                isImageTooLarge = image.height > 2500 || image.width > 2500
            }
            MovieImageProvider(
                url = if (isImageTooLarge || image == null) "null" else image.url,
                contentDescription = "Movie poster",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(180.dp)
                    .widthIn(max = 140.dp),
                error = painterResource(R.drawable.ic_launcher_background)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    genres?.forEach {
                        Text(
                            text = it,
                            modifier = Modifier
                                .height(intrinsicSize = IntrinsicSize.Min)
                                .padding(end = 0.dp)
                                .border(
                                    BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(start = 5.dp, end = 5.dp, top = 8.dp, bottom = 8.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                val n = plot ?: stringResource(R.string.no_plot)
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = limitToFirstSentence(n),
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        WatchListButton()
    }
}

/**
 * Animated button to add movie to watchList, Currently
 * not implemented (needs user feature)
 * */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WatchListButton() {
    var isWatchListed by remember { mutableStateOf(false) }   //User feature not implemented

    AnimatedContent(
        targetState = isWatchListed, label = "watchedAnimation",
        //transitionSpec = {}
    ) {
        val icon = if (it) Icons.Default.Check else Icons.Default.Add
        val text = if (it) stringResource(R.string.added_to_watchlist) else stringResource(R.string.add_to_watchlist)
        val borderSize = if (it) 1.dp else 0.dp
        val textColor = if (it) MaterialTheme.colorScheme.onBackground else Color.Black

        Card(
            border = BorderStroke(
                borderSize, MaterialTheme.colorScheme.primary
            ), shape = RoundedCornerShape(6.dp)
        ) {
            Row(
                if (it) Modifier.fillMaxWidth() else Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    isWatchListed = !isWatchListed
                }) { //Click action, animation trigger
                    Icon(
                        imageVector = icon, contentDescription = stringResource(R.string.add_to_playlist), tint = textColor
                    )
                }
                Column {
                    Text(
                        color = textColor,
                        style = MaterialTheme.typography.bodyMedium,
                        text = text,
                    )
                    Text(
                        color = textColor,
                        style = MaterialTheme.typography.bodySmall,
                        text = "Added by 100K users",   //not queried
                    )
                }
            }
        }
    }
}

@Composable
fun LongDivider() {
    Divider(thickness = 2.dp,
        modifier = Modifier
            .padding(top = 20.dp, bottom = 10.dp)
            .layout { measurable, constraints ->
                val placeable = measurable.measure(
                    constraints.copy(
                        maxWidth = constraints.maxWidth + 20.dp.roundToPx(), //add the end padding 16.dp
                    )
                )
                layout(placeable.width, placeable.height) {
                    placeable.place(0, 0)
                }
            })
}

//User Ratings section that uses a grid
@Composable
fun RatingSection(
    userRaters: Int? = 0, rating: Double? = 0.0, metaScore: Int, metaCriticsNum: Int
) {
    val backgroundColor = if (metaScore > 70) MovieColors.DeepGreen else Color.Red

    val star = rememberStarRate(
        fillColor = Color.Yellow,
    )

    /*Experimental use of External Library
    * use of Row in every item to center*/
    Column(Modifier.fillMaxWidth()) {
        GridPad(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth(),
            cells = GridPadCells.Builder(rowCount = 3, columnCount = 3)
                .rowSize(index = 0, size = GridPadCellSize.Weight(2f)).build()
        ) {
            item {
                Image(star, contentDescription = "ratings")
            }
            item { MutableRatingStar() }
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        text = metaScore.toString(),
                        modifier = Modifier.background(backgroundColor)
                    )
                }
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "$rating",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text("/10")
                }
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Rate this", color = MaterialTheme.colorScheme.secondary)
                }
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "MetaScore", modifier = Modifier)
                }
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "$userRaters critics",
                        modifier = Modifier,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top
                ) {
                    Text(text = "", style = MaterialTheme.typography.bodySmall)
                }
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "$metaCriticsNum critics",
                        modifier = Modifier,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}


/**
 * Animated button to add notification for updates, Currently
 * not implemented (needs user feature)
 * */
@Preview
@Composable
fun NotificationSection() {
    var isChecked by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = !isChecked, exit = fadeOut(
            animationSpec = tween(300, 0, easing = EaseOut)
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(0.8f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Notifications, contentDescription = stringResource(R.string.notifs))
                    Text(
                        text = stringResource(R.string.turn_on_notifs), style = MaterialTheme.typography.titleMedium
                    )
                }
                //  Spacer(Modifier.padding(bottom = 5.dp))
                Text(
                    text = stringResource(R.string.get_notified),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
            Switch(modifier = Modifier.scale(0.7f),
                checked = isChecked,
                thumbContent = { SwitchDefaults.IconSize },
                onCheckedChange = { isChecked = it })
        }
    }
}

/**
 * Composable that represents parental Guidance
 * @param parentalGuides, a list of [ParentalGuide] objects that
 * represents each of the categories of extreme content
 * @param onContentAdvisoryClick method that triggers navigation
 * [ContentAdvisoryScreen]
 * */
@Composable
fun ParentsGuideSection(
    parentalGuides: List<ParentalGuide>,
    onContentAdvisoryClick: () -> Unit
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .height(25.dp)
                        .width(3.dp)
                        .background(MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = stringResource(R.string.parents_guide),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(text = stringResource(R.string.see_all),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(color = MaterialTheme.colorScheme.primary)
                    ) { onContentAdvisoryClick() }
            )
        }

        SpaceH(side = 20.dp)

        Column(modifier = Modifier
            .padding(start = 5.dp)
            .clickable {
                onContentAdvisoryClick()
            }) {
            Text(text = stringResource(R.string.content_rating), modifier = Modifier.padding(bottom = 0.dp))
            Text(
                text = stringResource(R.string.view_content_advisory),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .alpha(0.7f)
            )
        }

        SpaceH(20.dp)

        Column(modifier = Modifier.padding(start = 10.dp)) {
            parentalGuides.forEach { guide ->
                val bgColor: Color = when (guide.severityVotes.status) {
                    "none" -> Color.Green
                    "mild" -> Color.Yellow
                    "moderate" -> MovieColors.Orange
                    "severe" -> Color.Red
                    else -> {
                        Color.Transparent
                    }
                }
                val label: String = when (guide.label) {
                    "nudity" -> stringResource(R.string.sex_and_nudity)
                    "violence" -> stringResource(R.string.violence_and_gore)
                    "profanity" -> stringResource(R.string.profanity)
                    "alcohol" -> stringResource(R.string.alcohol_drugs_and_smoking)
                    "frightening" -> stringResource(R.string.frightening_scenes)
                    else -> {
                        " "
                    }
                }
                val guideValue = guide.severityVotes.status.replaceFirstChar { it.uppercase() }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Card(
                        modifier = Modifier
                            .height(25.dp)
                            .width(12.dp),
                        shape = RoundedCornerShape(1.dp),
                        colors = CardDefaults.cardColors(containerColor = bgColor),
                        content = {}
                    )
                    SpaceW(side = 10.dp)
                    Text(label)
                    Text(guideValue, modifier = Modifier.alpha(0.5f))
                }
                SpaceH(10.dp)
            } //end of loop
        }
    }
}


//@Preview
//@Composable
//fun PreviewSec() {
//    RatingSection(userRaters = 155455, metaScore = 90, metaCriticsNum = 15, rating = 8.2)
//}

fun limitToFirstSentence(text: String): String {
    val firstSentenceEnd = text.indexOfAny(charArrayOf('.', '!', '?'))
    return if (firstSentenceEnd != -1) {
        text.substring(0, firstSentenceEnd + 1)
    } else {
        text
    }
}


fun timeToStr(minutes: Int): String {
    val hours = minutes / 60
    val minutesStr = if (minutes % 60 < 10) {
        "0${minutes % 60}"
    } else {
        "${minutes % 60}"
    }
    return if (hours > 0) {
        "${hours}h ${minutesStr}m"
    } else {
        "${minutesStr}m"
    }
}


object MovieColors {
    val DeepGreen = Color(0xFF388E3C)
    val Orange = Color(0xFFF57C00)
}