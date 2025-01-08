package com.example.saveshare.data.local

import androidx.paging.PagingData
import com.example.saveshare.data.models.Video
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    suspend fun getLocalVideosList(): List<Video>

    suspend fun getFavouriteVideos(): Flow<PagingData<Video>>

    suspend fun getFavouriteVideosList(): List<Video>

    suspend fun save(videos: List<Video>)

    suspend fun deleteAll()
}