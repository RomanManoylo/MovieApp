package com.example.saveshare.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "video")
data class Video(
    @PrimaryKey
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("overview") val description: String,
    @field:SerializedName("poster_path") val posterPath: String?,
    @field:SerializedName("release_date") val releaseDate: String,
    @field:SerializedName("vote_average") val voteAverage: Float,
    @field:SerializedName("vote_count") val voteCount: Int,
    @ColumnInfo(defaultValue = "0") var isFavourite: Boolean = false
)
