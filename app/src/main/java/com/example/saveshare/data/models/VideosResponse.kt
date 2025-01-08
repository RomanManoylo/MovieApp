package com.example.saveshare.data.models

import com.google.gson.annotations.SerializedName

data class VideosResponse(
    @field:SerializedName("results") val results: List<Video>,
    @field:SerializedName("page") val totalPages: Int
)
