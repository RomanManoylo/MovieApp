package com.example.saveshare.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.saveshare.data.local.ILocalDataSource
import com.example.saveshare.data.models.RequestParams
import com.example.saveshare.data.models.Video
import com.example.saveshare.data.local.LocalDataSource
import com.example.saveshare.data.remote.IRemoteDataSource
import com.example.saveshare.data.remote.RemoteDataSource
import com.example.saveshare.data.remote.VideosPagingSource
import com.example.saveshare.domain.repository.VideoRepository
import com.example.saveshare.util.AppConstants
import kotlinx.coroutines.flow.Flow

open class VideosRepositoryImpl(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource
) : VideoRepository {

    override suspend fun getPagingData(params: RequestParams): Flow<PagingData<Video>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = AppConstants.Paging.PAGE_SIZE),
            pagingSourceFactory = { VideosPagingSource(remoteDataSource, localDataSource, params) }
        ).flow
    }

    override suspend fun getFavouriteVideos(): Flow<PagingData<Video>> {
        return localDataSource.getFavouriteVideos()
    }

   override suspend fun saveVideo(video: Video) {
        localDataSource.save(listOf(video))
    }
}