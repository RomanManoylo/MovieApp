package com.example.saveshare.ui.screens

import com.example.saveshare.data.models.Video

sealed class VideoEvent {
    data class OnVideoLicked(val video: Video) : VideoEvent()
    data object OnDataRefresh : VideoEvent()
}

sealed class LoadingState {
    data object Loading : LoadingState()
    data object Idle : LoadingState()
    data class Error(val message: String) : LoadingState()
}