package com.keeghan.movieinfo.network


import com.keeghan.movieinfo.models.movieOverview.MovieOverViewResponse
import com.keeghan.movieinfo.models.popularMovies.PopularMovieResponse
import com.keeghan.movieinfo.models.shows.ShowsResponse
import retrofit2.Response
import retrofit2.http.*


interface IMDBApi {

    @GET("v2/find")
    suspend fun findTitle(
        @Query("title") title: String,
        @Query("paginationKey") page: Int?
    ): Response<ShowsResponse>

    @GET("get-base")
    suspend fun findOverView(
        @Query("tconst") id: String
    ): Response<MovieOverViewResponse>

    @GET("get-most-popular-movies")
    suspend fun getPopularMovies(
    ): Response<PopularMovieResponse>

//    @GET("v2/find")
//    suspend fun findTitle(
//        @Query("title") title: String,
//        @Query("paginationKey") page: String
//    ): Response<ShowsResponse>

//    //Product Methods
//    @GET("products/{id}")
//    suspend fun findTitle(@Path("id") productId: Int):
//
//    @GET //uses url
//    suspend fun getAllProduct(@Url url: String):
//
//    @POST("products")
//    suspend fun createProduct(
//        @Header("Authorization") token: String,
//        @Body productRequest: ProductReq,
//    ): Response<>
//
//    @DELETE("products/{id}")
//    suspend fun deleteProduct(
//        @Header("Authorization") token: String,
//        @Path("id") productId: Int,
//    ): Response<Unit>
}

