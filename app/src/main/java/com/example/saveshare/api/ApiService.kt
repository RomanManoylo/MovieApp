package com.example.saveshare.api

import com.example.saveshare.data.models.VideosResponse
import com.example.saveshare.util.AppConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    suspend fun loadRemoteVideos(
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String,
        @Query("vote_average.gte") voteAverage: Float,
        @Query("vote_count.gte") voteCount: Float,
        @Query("api_key") apiKey: String = AppConstants.Network.API_KEY
    ): VideosResponse
}