package com.example.saveshare.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.saveshare.data.local.ILocalDataSource
import com.example.saveshare.data.models.RequestParams
import com.example.saveshare.data.models.Video
import com.example.saveshare.util.AppConstants

class VideosPagingSource(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource,
    private val requestParams: RequestParams
) : PagingSource<Int, Video>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Video> {
        val page = params.key ?: AppConstants.Paging.STARTING_PAGE_INDEX
        return try {
            val response = remoteDataSource.getRemoteVideos(page, requestParams)
            val videos =
                response.results
            if (page == AppConstants.Paging.STARTING_PAGE_INDEX) {//small trick, in case when data from BE overwrites the data about favourites video
                val favourites = localDataSource.getFavouriteVideosList()
                localDataSource.deleteAll()
                localDataSource.save(videos)
                localDataSource.save(favourites)
            }
            LoadResult.Page(
                data = videos.sortedByDescending { it.releaseDate },
                prevKey = if (page == AppConstants.Paging.STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (videos.isEmpty() || page + 1 > response.totalPages) null else page + 1
            )
        } catch (exception: Exception) {
            return try {
                if (page != AppConstants.Paging.STARTING_PAGE_INDEX) {
                    LoadResult.Error(exception)
                } else {
                    LoadResult.Page(
                        data = localDataSource.getLocalVideosList(),
                        prevKey = null,
                        nextKey = null
                    )
                }
            } catch (exception: Exception) {
                LoadResult.Error(exception)
            }
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Video>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}