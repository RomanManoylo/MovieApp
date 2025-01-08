package com.example.saveshare.ui.screens.videos

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.saveshare.data.models.Video
import com.example.saveshare.ui.components.RefreshPage
import com.example.saveshare.ui.screens.VideoEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import org.koin.androidx.compose.koinViewModel

@Destination<RootGraph>(start = true)
@Composable
fun VideosScreen(viewModel: VideosViewModel = koinViewModel()) {
    VideoScreenContent(viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoScreenContent(viewModel: VideosViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val pagingItems: LazyPagingItems<Video> =
        viewModel.videoList.collectAsLazyPagingItems()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = { viewModel.onEvent(VideoEvent.OnDataRefresh) }
    )

    RefreshPage(uiState, pagingItems, pullRefreshState, false, viewModel::onEvent)
}



