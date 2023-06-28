package com.keeghan.movieinfo.repository.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.keeghan.movieinfo.models.shows.Result
import com.keeghan.movieinfo.network.IMDBApi
import retrofit2.HttpException
import java.io.IOException


class MoviePagingSource(
    private val api: IMDBApi,
    private val title: String,
    private val types: String?
) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {

        return try {
            val page = params.key ?: 1
            val response = api.findTitle(title = title, page = page, type = types)
            val data = response.body()?.results ?: emptyList()
            Log.i("findTitle", "pagingsource")

            if (!response.isSuccessful) {
                return LoadResult.Error(Exception(response.message()))
            } else {
                if (response.body()?.results == null) {
                    return LoadResult.Error(Exception("no matches"))
                }
            }

            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (data.isEmpty()) null else page.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}


fun extractId(url: String): String? {
    val pattern = "/title/(\\w+)/".toRegex()
    val matchResult = pattern.find(url)
    return matchResult?.groupValues?.get(1)
}