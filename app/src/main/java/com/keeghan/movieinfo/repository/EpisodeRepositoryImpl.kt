package com.keeghan.movieinfo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keeghan.movieinfo.models.movieOverview.MovieOverViewResponse
import com.keeghan.movieinfo.models.shows.Result
import com.keeghan.movieinfo.network.IMDBApi
import com.keeghan.movieinfo.repository.paging.MoviePagingSource
import com.keeghan.movieinfo.repository.paging.extractIMDbIDs
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodeRepositoryImpl
@Inject constructor(private val api: IMDBApi) : EpisodeRepository {

//    override suspend fun findTitle(title: String, page: Int?): Response<ShowsResponse> {
//        return api.findTitle(title, page)
//    }

    override suspend fun findTitle(title: String): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
            ),
            pagingSourceFactory = {
                MoviePagingSource(api, title)
            }
        ).flow
    }

    override suspend fun findPopularTitles(): List<MovieOverViewResponse> {
        val response = api.getPopularMovies()
        val popular = response.body()?.let { extractIMDbIDs(it) } ?: emptyList()

        val movieList: MutableList<MovieOverViewResponse> = mutableListOf()

        popular.map { it ->
            try {
                val tempResponse = api.findOverView(it)
                if (tempResponse.isSuccessful) {
                    movieList.add(tempResponse.body()!!)
                }
            } catch (_: Exception) {
                //Todo
            }
        }
        return movieList
    }


}