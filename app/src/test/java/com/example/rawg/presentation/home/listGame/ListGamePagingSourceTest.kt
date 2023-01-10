package com.example.rawg.presentation.home.listGame

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams.Refresh
import androidx.paging.PagingSource.LoadResult.Page
import com.example.rawg.domain.RawgInteractor
import com.example.rawg.domain.dto.GameDto
import com.example.rawg.domain.dto.ListGameDto
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
internal class ListGamePagingSourceTest {
    private val interactor: RawgInteractor = mock()

    companion object {
        const val query = "query"
    }

    private val fakePost = mapOf(
        "search" to "",
        "page" to "1",
        "page_size" to "10"
    )

    private val fakePostQuery = mapOf(
        "search" to query,
        "page" to "1",
        "page_size" to "10"
    )

    val fakeError: Exception by lazy {
        Exception("error")
    }

    private val fakeListResponse = ListGameDto(
        listOf(
            GameDto(),
            GameDto(),
            GameDto(),
            GameDto(),
            GameDto(),
            GameDto(),
            GameDto(),
            GameDto(),
            GameDto(),
            GameDto(),
        )
    )


    @Test
    fun `Load padding data return error`() = runBlocking {
        val pagingSource = ListGamePagingSource(interactor, query)

        whenever(
            interactor.getListGame(fakePostQuery)
        ).thenReturn(fakeListResponse)

        val loadParams: PagingSource.LoadParams<Int> = Refresh(
            key = 1,
            loadSize = 10,
            placeholdersEnabled = false
        )

        whenever(
            pagingSource.load(loadParams)
        ).thenReturn(PagingSource.LoadResult.Error(fakeError))

        val request = try {
            pagingSource.load(loadParams)
        } catch (e: Exception) {
            e
        }

        val expected = Page(
            data = fakeListResponse.results,
            prevKey = null,
            nextKey = 2
        )

        verify(interactor).getListGame(fakePostQuery)

        assertNotEquals(
            illegal = expected,
            actual = request
        )
    }

    @Test
    fun `Load padding data with query success`() = runTest {
        val pagingSource = ListGamePagingSource(interactor, query)

        whenever(
            interactor.getListGame(fakePostQuery)
        ).thenReturn(fakeListResponse)

        val loadParams: PagingSource.LoadParams<Int> = Refresh(
            key = 1,
            loadSize = 10,
            placeholdersEnabled = false
        )
        val request = pagingSource.load(loadParams)

        val expected = Page(
            data = fakeListResponse.results,
            prevKey = null,
            nextKey = 2
        )

        verify(interactor).getListGame(fakePostQuery)

        assertEquals(
            expected = expected,
            actual = request
        )
    }

    @Test
    fun `Load padding data with load more success`() = runTest {
        val pagingSource = ListGamePagingSource(interactor, "")

        whenever(
            interactor.getListGame(fakePost)
        ).thenReturn(fakeListResponse)

        val loadParams: PagingSource.LoadParams<Int> = Refresh(
            key = 1,
            loadSize = 10,
            placeholdersEnabled = false
        )
        val request = pagingSource.load(loadParams)

        val expected = Page(
            data = fakeListResponse.results,
            prevKey = null,
            nextKey = 2
        )

        verify(interactor).getListGame(fakePost)

        assertEquals(
            expected = expected,
            actual = request
        )
    }

    @Test
    fun `Load padding data with end load success`() = runTest {
        val pagingSource = ListGamePagingSource(interactor, "")

        val fakeResponse = ListGameDto()

        whenever(
            interactor.getListGame(fakePost)
        ).thenReturn(fakeResponse)

        val loadParams: PagingSource.LoadParams<Int> = Refresh(
            key = null,
            loadSize = 10,
            placeholdersEnabled = false
        )
        val request = pagingSource.load(loadParams)

        val expected = Page(
            data = fakeResponse.results,
            prevKey = null,
            nextKey = null,
        ).toString()

        verify(interactor).getListGame(fakePost)

        assertEquals(
            expected = expected,
            actual = request.toString(),
        )
    }

}