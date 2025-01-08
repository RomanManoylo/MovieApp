package com.example.saveshare.data.remote

import com.example.saveshare.data.models.RequestParams
import com.example.saveshare.data.models.VideosResponse

interface IRemoteDataSource {
    suspend fun getRemoteVideos(page: Int, params: RequestParams): VideosResponse
}