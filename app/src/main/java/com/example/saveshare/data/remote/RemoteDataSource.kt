package com.example.saveshare.data.remote

import com.example.saveshare.api.ApiService
import com.example.saveshare.data.models.RequestParams
import com.example.saveshare.data.models.VideosResponse

open class RemoteDataSource(private val service: ApiService): IRemoteDataSource {

    override suspend fun getRemoteVideos(page: Int, params: RequestParams): VideosResponse {
        return service.loadRemoteVideos(
            page,
            sortBy = params.sortBy.keyWord,
            voteAverage = params.voteAverage,
            voteCount = params.voteCount
        )
    }
}