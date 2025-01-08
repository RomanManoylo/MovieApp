package com.example.saveshare.ui.screens.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.saveshare.data.models.Video
import com.example.saveshare.domain.usecases.IVideoUseCase
import com.example.saveshare.ui.screens.LoadingState
import com.example.saveshare.ui.screens.VideoEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class FavouritesViewModel(
    private val videoUseCase: IVideoUseCase
) : ViewModel(), KoinComponent {

    private val _favouriteVideoList = MutableStateFlow<PagingData<Video>?>(null)
    val favouriteVideoList: Flow<PagingData<Video>> get() = _favouriteVideoList.filterNotNull()

    private val _uiState = MutableStateFlow<LoadingState>(LoadingState.Loading)
    val uiState: StateFlow<LoadingState> = _uiState.asStateFlow()

    init {
        loadFavourite()
    }

    private fun loadFavourite() {
        viewModelScope.launch {
            _uiState.value = LoadingState.Loading
            try {
                _favouriteVideoList.value =
                    videoUseCase.getFavouriteVideos().cachedIn(viewModelScope).first()
                _uiState.value = LoadingState.Idle
            } catch (e: Exception) {
                _uiState.value = LoadingState.Error("Failed to load")
                e.printStackTrace()
            }
        }
    }

    private fun updateFavouriteState(video: Video) {
        viewModelScope.launch {
            try {
                videoUseCase.saveVideo(video.apply { isFavourite = false })
                loadFavourite()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onEvent(event: VideoEvent) {
        viewModelScope.launch {
            when (event) {
                is VideoEvent.OnVideoLicked -> {
                    updateFavouriteState(event.video)
                }

                is VideoEvent.OnDataRefresh -> {
                    loadFavourite()
                }
            }
        }
    }
}