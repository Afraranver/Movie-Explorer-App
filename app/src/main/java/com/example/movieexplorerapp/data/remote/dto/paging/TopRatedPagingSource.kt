package com.example.movieexplorerapp.data.remote.dto.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieexplorerapp.common.Constants.Network.PAGE_SIZE
import com.example.movieexplorerapp.common.Constants.Network.STARTING_PAGE_INDEX
import com.example.movieexplorerapp.data.remote.TMDbApiService
import com.example.movieexplorerapp.data.remote.dto.model.movies.MovieItem

import retrofit2.HttpException
import java.io.IOException

class TopRatedPagingSource(private val apiService: TMDbApiService, private val lang:String) : PagingSource<Int, MovieItem>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val data = apiService.getTopRated(
                page = position,
                language = lang
            )
            val nextKey = if (data.results?.isEmpty() == true) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / PAGE_SIZE)
            }
            val prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1
            LoadResult.Page(
                data = data.results!!,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition
    }
}