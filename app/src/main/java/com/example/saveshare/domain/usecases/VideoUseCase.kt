package com.example.saveshare.domain.usecases

import androidx.paging.PagingData
import com.example.saveshare.data.models.RequestParams
import com.example.saveshare.data.models.Video
import com.example.saveshare.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow

//use case is  not mandatory in this case, to keep these classes simple and lightweight
open class VideoUseCase(private val repository: VideoRepository) : IVideoUseCase {
    override suspend fun getPagingData(params: RequestParams): Flow<PagingData<Video>> {
        return repository.getPagingData(params)
    }

    override suspend fun getFavouriteVideos(): Flow<PagingData<Video>> {
        return repository.getFavouriteVideos()
    }

    override suspend fun saveVideo(video: Video) {
        repository.saveVideo(video)
    }
}