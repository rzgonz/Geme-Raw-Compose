package com.example.rawg.presentation.home.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.common.common.Async
import com.common.common.Fail
import com.common.common.Loading
import com.common.common.Success
import com.common.common.Uninitialized
import com.common.common.ext.logD
import com.example.rawg.domain.RawgInteractor
import com.example.rawg.domain.dto.GameDetailDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FavoriteState(
    val detailGame: Async<List<GameDetailDto>> = Uninitialized
)

class FavoriteViewModel(
    private val rawgInteractor: RawgInteractor
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteState())
    val state = _state.asStateFlow()

    init {

    }

    fun getListGameFavorite() {
        _state.update { it.copy(detailGame = Loading()) }
        viewModelScope.launch {
            try {
                val data = rawgInteractor.getListGameFavorite()
                logD<FavoriteViewModel>("room : ${data.size}")
                _state.update { it.copy(detailGame = Success(data)) }
            } catch (e: Exception) {
                _state.update { it.copy(detailGame = (Fail(e))) }
            }
        }
    }

    fun deleteFromFavorite(detailDto: GameDetailDto) {
        viewModelScope.launch {
            rawgInteractor.deleteFromFavorite(detailDto)
            getListGameFavorite()
        }
    }
}