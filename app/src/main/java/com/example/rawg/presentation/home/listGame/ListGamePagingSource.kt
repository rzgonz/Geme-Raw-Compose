package com.example.rawg.presentation.home.listGame

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rawg.domain.RawgInteractor
import com.example.rawg.domain.dto.GameDto
import retrofit2.HttpException
import java.io.IOException

class ListGamePagingSource(
    private val rawgInteractor: RawgInteractor,
    val query: String = ""
) : PagingSource<Int, GameDto>() {
    override fun getRefreshKey(state: PagingState<Int, GameDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameDto> {
        return try {
            val nextPage = params.key ?: 1
            val data =
                mapOf(
                    "search" to query,
                    "page" to nextPage.toString(),
                    "page_size" to "10"
                )
            val userList = rawgInteractor.getListGame(data)
            LoadResult.Page(
                data = userList.results,
                prevKey = null,
                nextKey = if (userList.results.isEmpty()) null else nextPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}