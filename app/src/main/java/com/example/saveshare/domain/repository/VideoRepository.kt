package com.example.saveshare.domain.repository

import androidx.paging.PagingData
import com.example.saveshare.data.models.RequestParams
import com.example.saveshare.data.models.Video
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    suspend fun getPagingData(params: RequestParams): Flow<PagingData<Video>>
    suspend fun getFavouriteVideos(): Flow<PagingData<Video>>
    suspend fun saveVideo(video: Video)
}