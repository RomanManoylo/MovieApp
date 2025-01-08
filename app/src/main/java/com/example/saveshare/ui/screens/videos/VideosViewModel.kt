package com.example.saveshare.ui.screens.videos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.saveshare.data.models.RequestParams
import com.example.saveshare.data.models.SortBy
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

class VideosViewModel(
    private val videoUseCase: IVideoUseCase
) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow<LoadingState>(LoadingState.Loading)
    val uiState: StateFlow<LoadingState> = _uiState.asStateFlow()

    private val _videoList = MutableStateFlow<PagingData<Video>?>(null)
    val videoList: Flow<PagingData<Video>> get() = _videoList.filterNotNull()

    init {
        refreshData()
    }

    private fun refreshData() {
        viewModelScope.launch {
            _uiState.value = LoadingState.Loading
            try {
                _videoList.value =
                    videoUseCase.getPagingData(RequestParams(sortBy = SortBy.RELEASE_DATE_DESC))
                        .cachedIn(viewModelScope).first()
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
                videoUseCase.saveVideo(video.apply { isFavourite = true })
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
                    refreshData()
                }
            }
        }
    }
}



