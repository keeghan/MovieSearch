package com.keeghan.movieinfo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keeghan.movieinfo.models.MovieImagesResponse
import com.keeghan.movieinfo.models.MovieOverViewResponse
import com.keeghan.movieinfo.models.MovieParentalGuideResponse
import com.keeghan.movieinfo.models.shows.Result
import com.keeghan.movieinfo.network.IMDBApi
import com.keeghan.movieinfo.repository.paging.MoviePagingSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class EpisodeRepositoryImpl
@Inject constructor(private val api: IMDBApi) : EpisodeRepository {


//    override suspend fun findTitle(title: String, page: Int?): Response<ShowsResponse> {
//        return api.findTitle(title, page)
// }

    override suspend fun findTitle(title: String, types: String?): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
            ),
            pagingSourceFactory = {
                MoviePagingSource(api, title = title, types = types)
            }
        ).flow
    }

    override suspend fun findOverView(title: String): Response<MovieOverViewResponse> {
        return api.findOverView(title)
    }

    override suspend fun getImages(title: String): Response<MovieImagesResponse> {
        return api.getImages(title)
    }

    override suspend fun getParentalGuide(title: String): Response<MovieParentalGuideResponse> {
        return api.getParentalGuide(title)
    }

}