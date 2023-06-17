package com.keeghan.movieinfo.repository.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.keeghan.movieinfo.models.movieOverview.MovieOverViewResponse
import com.keeghan.movieinfo.network.IMDBApi
import retrofit2.HttpException
import java.io.IOException


class PopularMoviesPagingSource(
    private val api: IMDBApi,
) : PagingSource<Int, MovieOverViewResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieOverViewResponse> {
        return try {
            val page = params.key ?: 1

            //get all ids in popularTitles
            val response = api.getPopularMovies()
            val popular = response.body()?.let { extractIMDbIDs(it) } ?: emptyList()

            val movies = popular.map { imdbID ->
                val tempResponse = api.findOverView(imdbID)
                if (tempResponse.isSuccessful) {
                    tempResponse.body()!!
                } else {
                    throw Exception(tempResponse.message())
                }
            }.toMutableList()

            Log.i("findTitle", "pagingsource")

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (movies.isEmpty()) null else page.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieOverViewResponse>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
    }
}

//remove slashes from titles
fun extractIMDbIDs(urls: List<String>): List<String> {
    val imdbIDs = mutableListOf<String>()
    for (url in urls) {
        val imdbID = url.substringAfterLast("e/").removeSuffix("/")
        imdbIDs.add(imdbID)
    }
    return imdbIDs
}

fun extractId(id: String): String {
    return id.substringAfterLast("e/").removeSuffix("/")
}