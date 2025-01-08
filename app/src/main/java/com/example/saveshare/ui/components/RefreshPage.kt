package com.example.saveshare.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.saveshare.data.models.Video
import com.example.saveshare.ui.screens.LoadingState
import com.example.saveshare.ui.screens.VideoEvent
import eu.bambooapps.material3.pullrefresh.PullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshPage(
    uiState: LoadingState,
    pagingItems: LazyPagingItems<Video>,
    pullRefreshState: PullRefreshState,
    isFavourite: Boolean = false,
    onEvent: (VideoEvent) -> Unit = {}
) {
    when (uiState) {
        is LoadingState.Loading -> {
            Loading()
        }

        is LoadingState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = uiState.message,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        is LoadingState.Idle -> {
            VideoList(pagingItems = pagingItems, pullRefreshState, isFavourite) {
                onEvent(VideoEvent.OnVideoLicked(it))
            }
        }
    }
}