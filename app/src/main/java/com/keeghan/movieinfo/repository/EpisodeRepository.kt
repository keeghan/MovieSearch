package com.keeghan.movieinfo.repository

import androidx.paging.PagingData
import com.keeghan.movieinfo.models.MovieImagesResponse
import com.keeghan.movieinfo.models.MovieOverViewResponse
import com.keeghan.movieinfo.models.MovieParentalGuideResponse
import com.keeghan.movieinfo.models.shows.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface EpisodeRepository {

    suspend fun findOverView(title: String): Response<MovieOverViewResponse>
    suspend fun getImages(title: String): Response<MovieImagesResponse>
    suspend fun getParentalGuide(title: String): Response<MovieParentalGuideResponse>
    suspend fun findTitle(title: String, types: String?): Flow<PagingData<Result>>
}