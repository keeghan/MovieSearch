package com.keeghan.movieinfo.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.keeghan.movieinfo.models.movieOverview.MovieOverViewResponse
import com.keeghan.movieinfo.models.popularMovies.PopularMovieResponse
import com.keeghan.movieinfo.models.shows.Result
import com.keeghan.movieinfo.models.shows.ShowsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface EpisodeRepository {

     suspend fun findTitle(title: String): Flow<PagingData<Result>>

     suspend fun findPopularTitles(): List<MovieOverViewResponse>

}