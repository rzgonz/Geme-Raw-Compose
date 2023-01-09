package com.example.rawg

import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rawg.domain.RawgInteractor
import com.example.rawg.presentation.home.listGame.ListGamePagingSource
import com.example.rawg.presentation.home.listGame.ListGameViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ListGameViewModelTest {
    private val interactor: RawgInteractor = mock()
    private lateinit var SUT: ListGameViewModel
    private lateinit var pagin: ListGamePagingSource

    fun setUp() {
        SUT = ListGameViewModel(
            interactor
        )
        pagin = ListGamePagingSource(interactor)
    }

    @Test
    fun `search game query return success`() {
        val query = "cona"
        SUT.searchGame(query)

        val data =
            mapOf(
                "search" to query,
                "page" to 1,
                "page_size" to "10"
            )
        whenever(
            interactor.getListGame(
                data
            )
        )
            .thenReturn(Observable.just(fakeForumRoboguruSubjectDto))


        val value = pagin.query
        verify(interactor).getListGame()
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}