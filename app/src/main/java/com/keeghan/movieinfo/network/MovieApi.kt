package com.keeghan.movieinfo.network


import com.keeghan.movieinfo.models.MovieImagesResponse
import com.keeghan.movieinfo.models.MovieOverViewResponse
import com.keeghan.movieinfo.models.MovieParentalGuideResponse
import com.keeghan.movieinfo.models.shows.ShowsResponse
import retrofit2.Response
import retrofit2.http.*


interface MovieApi {


    @GET("v2/find")
    suspend fun findTitle(
        @Query("title") title: String,
        @Query("paginationKey") page: Int?,
        @Query("titleType") type: String?
    ): Response<ShowsResponse>

    @GET("get-overview-details")
    suspend fun findOverView(
        @Query("tconst") id: String
    ): Response<MovieOverViewResponse>

    @GET("get-images")
    suspend fun getImages(
        @Query("tconst") id: String,
        @Query("limit") limit: String = "5"
    ): Response<MovieImagesResponse>

    @GET("get-parental-guide")
    suspend fun getParentalGuide(
        @Query("tconst") id: String,
    ): Response<MovieParentalGuideResponse>


}

