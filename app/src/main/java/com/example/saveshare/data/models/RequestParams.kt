package com.example.saveshare.data.models

data class RequestParams(
    val sortBy: SortBy,
    val voteAverage: Float = 7f,
    val voteCount: Float = 100f
)//here can add new filtering params
