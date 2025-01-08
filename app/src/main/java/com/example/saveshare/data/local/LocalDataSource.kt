package com.example.saveshare.data.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.saveshare.data.models.Video
import com.example.saveshare.util.AppConstants
import kotlinx.coroutines.flow.Flow

open class LocalDataSource(private val dao: VideoDao): ILocalDataSource {

    override suspend fun getLocalVideosList():List<Video> = dao.getLocalVideos()

    override suspend fun getFavouriteVideos(): Flow<PagingData<Video>> = pagingVideo { dao.getFavouriteVideoPageList() }

    override suspend fun getFavouriteVideosList(): List<Video> = dao.getFavouriteVideoList()

    override suspend fun save(videos: List<Video>) {
        dao.addMovieList(videos)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }
}

private fun pagingVideo(block: () -> PagingSource<Int, Video>): Flow<PagingData<Video>> =
    Pager(PagingConfig(pageSize = AppConstants.Paging.PAGE_SIZE)) { block() }.flow
