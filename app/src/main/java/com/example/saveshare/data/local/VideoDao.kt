package com.example.saveshare.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.saveshare.data.models.Video

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieList(movieList: List<Video>)

    @Query("SELECT * FROM video")
    fun getLocalVideoList(): PagingSource<Int, Video>

    @Query("SELECT * FROM video ORDER BY releaseDate DESC LIMIT 20 ")
    suspend fun getLocalVideos(): List<Video>

    @Query("SELECT * FROM video WHERE isFavourite ORDER BY releaseDate DESC")
    fun getFavouriteVideoPageList(): PagingSource<Int, Video>

    @Query("SELECT * FROM video WHERE isFavourite ORDER BY releaseDate DESC")
    suspend fun getFavouriteVideoList(): List<Video>

    @Query("DELETE FROM video")
    suspend fun deleteAll()


}