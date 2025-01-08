package com.example.saveshare.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.saveshare.data.models.Video
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.PullRefreshState
import eu.bambooapps.material3.pullrefresh.pullRefresh

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoList(
    pagingItems: LazyPagingItems<Video>,
    state: PullRefreshState,
    isFavourite: Boolean = false,
    onFavouriteStateChanged: (Video) -> Unit
) {
    Box {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(state)
        ) {
            items(pagingItems.itemCount) { item ->

                val showHeader = item == 0 || !isYearAndMonthSame(
                    pagingItems[item]?.releaseDate,
                    pagingItems[item - 1]?.releaseDate
                )
                pagingItems[item]?.let {
                    VideoListItem(
                        it, showHeader, isFavourite
                    ) {
                        pagingItems[item]?.let { video ->
                            onFavouriteStateChanged.invoke(video)
                        }
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = false, state = state,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}


fun isValidDate(date: String?): Boolean {
    val regex = "^\\d{4}-\\d{2}-\\d{2}\$".toRegex() // check format yyyy-MM-dd
    return date?.let {
        regex.matches(it)
    } ?: false
}

fun isYearAndMonthSame(
    first: String?,
    second: String?
): Boolean { //TODO should be changed to date comparison
    if (!isValidDate(first) || !isValidDate(second)) return false
    return first?.substring(0, 7) == second?.substring(0, 7)
}
