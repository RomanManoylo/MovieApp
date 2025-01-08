package com.example.saveshare.ui.screens.favourites

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.saveshare.data.models.Video
import com.example.saveshare.ui.components.RefreshPage
import com.example.saveshare.ui.screens.VideoEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import org.koin.androidx.compose.koinViewModel

@Destination<RootGraph>
@Composable
fun FavouriteScreen(viewModel: FavouritesViewModel = koinViewModel()) {
    FavoriteScreenContent(viewModel)
    LaunchedEffect(Unit) {
        viewModel.onEvent(VideoEvent.OnDataRefresh)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreenContent(viewModel: FavouritesViewModel ) {
    val uiState by viewModel.uiState.collectAsState()
    val pagingItems: LazyPagingItems<Video> =
        viewModel.favouriteVideoList.collectAsLazyPagingItems()
    val isRefreshing by remember {
        mutableStateOf(false)
    }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.onEvent(VideoEvent.OnDataRefresh) }
    )

    RefreshPage(uiState, pagingItems, pullRefreshState, true, viewModel::onEvent)
}