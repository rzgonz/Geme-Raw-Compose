package com.example.rawg.presentation.home.listGame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.common.common.Async
import com.common.common.Uninitialized
import com.example.rawg.domain.RawgInteractor
import com.example.rawg.domain.dto.GameDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class ListGameViewModel(
    private val rawgInteractor: RawgInteractor
):ViewModel() {
    var listGameDto: Flow<PagingData<GameDto>> = Pager(PagingConfig(pageSize = 10)) {
        ListGamePagingSource(rawgInteractor)
    }.flow.cachedIn(viewModelScope)

    fun searchGame(query :String) {
       listGameDto =  Pager(PagingConfig(pageSize = 10)) {
           ListGamePagingSource(rawgInteractor,query)
       }.flow.cachedIn(viewModelScope)
    }
}