package com.keeghan.movieinfo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.keeghan.movieinfo.models.shows.Image
import com.keeghan.movieinfo.models.shows.ImageX
import com.keeghan.movieinfo.models.shows.ParentTitle
import com.keeghan.movieinfo.models.shows.Result
import com.keeghan.movieinfo.ui.components.MovieCard
import com.keeghan.movieinfo.utils.Space
import com.keeghan.movieinfo.utils.smallSpace
import com.keeghan.movieinfo.viewModel.EpisodeViewModel
import com.keeghan.movieinfo.viewModel.SearchScreenUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    navController: NavController = rememberNavController(),
    viewModel: EpisodeViewModel = hiltViewModel(),
    onMovieClick: (String) -> Unit
                ) {
    val focusManager = LocalFocusManager.current
    val titleState = remember { mutableStateOf(TextFieldValue("")) }
    val uiState = viewModel.uiState
//    var isButtonClicked by remember { mutableStateOf(false) }

    val movieResponse = viewModel.movieSearchResult.collectAsLazyPagingItems()

    Column(
        Modifier
            .then(modifier)
            .padding(4.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        //  verticalArrangement = Arrangement.Center
          ) {
        Space(side = 5.dp)
        Row(
            modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(value = titleState.value,
                              onValueChange = {
                                  titleState.value = it
                              },
                              label = { Text("Movie") },
                              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                              modifier = Modifier
                                  .weight(1f)
                                  .padding(bottom = 8.dp))

            FilledTonalIconButton(modifier = Modifier
                .size(48.dp)
                .padding(start = 4.dp), onClick = {
                viewModel.search(titleState.value.text)
                focusManager.clearFocus()
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                //Text(text = "Search")
            }
        }//search Bar
        smallSpace()
        if (movieResponse.itemCount > 0) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)) {
                items(count = movieResponse.itemCount) {
                    MovieCard(movie = movieResponse[it]!!) { id ->
                        onMovieClick(id)
                    }
                }

                item {
                    when (val state = movieResponse.loadState.append) {
                        is LoadState.Error -> {
                            Toast.makeText(
                                LocalContext.current,
                                state.error.message ?: "error",
                                Toast.LENGTH_SHORT).show()
                        }

                        is LoadState.Loading -> {
                            CircularProgressIndicator()
                        }

                        else -> {}
                    }
                }
            }
        } //end of lazyRow

        Space(side = 60.dp)

        //first time Load and error OutPut
        when (val state = movieResponse.loadState.refresh) {
            is LoadState.Error -> {
                Toast.makeText(
                    LocalContext.current,
                    state.error.message ?: "error",
                    Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }

        if (uiState.value == SearchScreenUiState.LOADING) {
            CircularProgressIndicator()
        }
    }
}


@Composable
fun SmallText(text: String?) {
    Text(
        text ?: "",
        color = MaterialTheme.colorScheme.primary,
        fontSize = 12.sp,
        modifier = Modifier.padding(end = 5.dp))
}


@Preview
@Composable
fun CardPreview() {
    val img = Image(100, "def", "", 4000)
    val imgx = ImageX(100, "def", "", 4000)
    val pt = ParentTitle("23", imgx, "Heero times", "show", 2003)

    val movie = Result(
        12,
        "3",
        img,
        "23",
        4,
        pt,
        "null",
        emptyList(),
        4,
        3,
        2002,
        2003,
        "Hone Kong - Madness of the goat",
        "tvshows",
        2004)
    MovieCard(movie = movie) {}
}