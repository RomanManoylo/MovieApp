package com.example.saveshare

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.testing.asSnapshot
import com.example.saveshare.data.local.ILocalDataSource
import com.example.saveshare.data.models.RequestParams
import com.example.saveshare.data.models.SortBy
import com.example.saveshare.data.models.Video
import com.example.saveshare.data.models.VideosResponse
import com.example.saveshare.data.remote.IRemoteDataSource
import com.example.saveshare.data.repository.VideosRepositoryImpl
import com.example.saveshare.domain.repository.VideoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.times
import org.mockito.kotlin.whenever

class VideoRepositoryTest {

    private lateinit var localDataSource: ILocalDataSource
    private lateinit var remoteDataSource: IRemoteDataSource
    private lateinit var repository: VideoRepository
    val testItems = listOf(
        Video(1, "1", "desc", null, "2025-01-07", 8.8f, 255, false),
        Video(1, "1", "desc", null, "2025-01-07", 8.8f, 255, false),
        Video(2, "2", "desc", null, "2025-01-07", 8.8f, 255, false),
        Video(3, "3", "desc", null, "2025-01-07", 8.8f, 255, false),
        Video(4, "4", "desc", null, "2025-01-07", 8.8f, 255, false),
        Video(5, "5", "desc", null, "2025-01-07", 8.8f, 255, false)
    )

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        localDataSource = mock(ILocalDataSource::class.java)
        remoteDataSource = mock(IRemoteDataSource::class.java)
        repository = VideosRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun test_load_items_save_to_database() = runTest {
        val response = VideosResponse(
            testItems, 1
        )
        val favouriteVideo = Video(
            3,
            "3", "desc",
            null, "2025-01-07",
            8.8f, 255,
            true
        )
        whenever(
            remoteDataSource.getRemoteVideos(
                1, RequestParams(sortBy = SortBy.RELEASE_DATE_DESC)
            )
        ).thenReturn(response)
        whenever(localDataSource.getFavouriteVideosList()).thenReturn(
            listOf(
                favouriteVideo
            )
        )
        whenever(localDataSource.getLocalVideosList()).thenReturn(
            emptyList()
        )
        val itemsSnapshot =
            repository.getPagingData(RequestParams(sortBy = SortBy.RELEASE_DATE_DESC)).asSnapshot()
        assertEquals(itemsSnapshot.map { it.id }, testItems.map { it.id })
        verify(localDataSource, times(1)).getFavouriteVideosList()
        verify(
            localDataSource,
            times(1)
        ).deleteAll()
        verify(localDataSource, times(1)).save(testItems)
        verify(localDataSource, times(1)).save(listOf(favouriteVideo))
    }

    @Test
    fun do_not_save_videos_for_second_plus_pages() = runTest {
        val secondPageItems = listOf(
            Video(6, "6", "desc", null, "2025-01-07", 8.8f, 255, false),
            Video(7, "7", "desc", null, "2025-01-07", 8.8f, 255, false),
            Video(8, "8", "desc", null, "2025-01-07", 8.8f, 255, false),
            Video(9, "9", "desc", null, "2025-01-07", 8.8f, 255, false),
        )
        val response = VideosResponse(
            testItems, 2
        )
        val favouriteVideo = Video(
            3,
            "3", "desc",
            null, "2025-01-07",
            8.8f, 255,
            true
        )
        whenever(
            remoteDataSource.getRemoteVideos(
                1, RequestParams(sortBy = SortBy.RELEASE_DATE_DESC)
            )
        ).thenReturn(response)
        whenever(
            remoteDataSource.getRemoteVideos(
                2,
                RequestParams(sortBy = SortBy.RELEASE_DATE_DESC)
            )
        ).thenReturn(
            VideosResponse(
                secondPageItems, 2,
            )
        )
        whenever(localDataSource.getFavouriteVideosList()).thenReturn(
            listOf(
                favouriteVideo
            )
        )
        whenever(localDataSource.getLocalVideosList()).thenReturn(
            emptyList()
        )
        val itemsSnapshot =
            repository.getPagingData(RequestParams(sortBy = SortBy.RELEASE_DATE_DESC)).asSnapshot()
        val allItems = listOf(testItems, secondPageItems).flatten()
        assertEquals(itemsSnapshot.map { it.id }, allItems.map { it.id })
        verify(localDataSource, times(0)).save(secondPageItems)
    }

    @Test
    fun when_error_occur_during_loading_api_data_show_local() = runTest {
        val localVideo = Video(
            3, "3",
            "desc", null,
            "2025-01-07", 8.8f,
            255, true
        )
        whenever(localDataSource.getLocalVideosList()).thenReturn(
            listOf(localVideo)
        )
        val itemsSnapshot =
            repository.getPagingData(RequestParams(sortBy = SortBy.RELEASE_DATE_DESC)).asSnapshot()
        assertEquals(itemsSnapshot.map { it.id }, listOf(localVideo).map { it.id })
        verify(localDataSource, times(1)).getLocalVideosList()
    }

}