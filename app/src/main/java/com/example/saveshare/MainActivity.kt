package com.example.saveshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.saveshare.ui.screens.favourites.FavouriteScreen
import com.example.saveshare.ui.screens.videos.VideosScreen
import com.example.saveshare.ui.theme.Purple
import com.example.saveshare.ui.theme.SaveShareTheme
import com.example.saveshare.ui.theme.Teal
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.rememberDestinationsNavigator

@ExperimentalMaterial3Api
@ExperimentalLayoutApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SaveShareTheme {
                val navController = rememberNavController()
                ContentMain(navController)
            }
        }
    }
}

@Preview(showBackground = true)
@ExperimentalMaterial3Api
@ExperimentalLayoutApi
@Composable
fun ContentMain(
    navController: NavHostController = NavHostController(LocalContext.current),
) {
    val topBar = BottomBarDestination.entries.toTypedArray()
    var tabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                if (tabIndex == 0) {
                    VideosScreen()
                } else {
                    FavouriteScreen()
                }
            }

        },
        topBar = {
            TabRow(
                selectedTabIndex = tabIndex,
                containerColor = Purple,
                indicator = { tabPositions ->
                    if (tabIndex < tabPositions.size) {
                        SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                            color = Teal
                        )
                    }
                }
            ) {
                topBar.forEachIndexed { index, item ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = {
                            tabIndex = index
                        },
                        text = {
                            Text(
                                text = stringResource(item.label),
                                color = if (tabIndex == index) Color.White else Teal
                            )
                        }
                    )
                }
            }
        }
    )
}

enum class BottomBarDestination(
    @StringRes val label: Int
) {
    Films(R.string.films),
    Favorite(R.string.favourite)
}



