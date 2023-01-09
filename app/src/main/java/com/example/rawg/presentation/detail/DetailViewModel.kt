package com.example.rawg.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.common.common.Async
import com.common.common.Fail
import com.common.common.Loading
import com.common.common.Success
import com.common.common.Uninitialized
import com.example.rawg.domain.RawgInteractor
import com.example.rawg.domain.dto.GameDetailDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GameDetailState(
    val detailGame: Async<GameDetailDto> = Uninitialized
)

class DetailViewModel(
    private val rawgInteractor: RawgInteractor
) : ViewModel() {

    private val _state = MutableStateFlow(GameDetailState())
    val state = _state.asStateFlow()

    fun getDetailGame(gameId: String) {
        _state.update { it.copy(detailGame = Loading()) }
        viewModelScope.launch {
            try {
                val data = rawgInteractor.getDetailgame(gameId)
                _state.update { it.copy(detailGame = Success(data)) }
            } catch (e: Exception) {
                _state.update { it.copy(detailGame = (Fail(e))) }
            }
        }
    }

    fun saveToFavorite(detailDto: GameDetailDto) {
        viewModelScope.launch {
            rawgInteractor.saveToFavorite(detailDto)
        }
    }

    fun deleteFromFavorite(detailDto: GameDetailDto) {
        viewModelScope.launch {
            rawgInteractor.deleteFromFavorite(detailDto)
        }
    }
}