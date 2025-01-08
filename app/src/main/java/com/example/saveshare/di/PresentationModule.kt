package com.example.saveshare.di

import com.example.saveshare.ui.screens.favourites.FavouritesViewModel
import com.example.saveshare.ui.screens.videos.VideosViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule= module {
    viewModel{ VideosViewModel(get()) }
    viewModel{ FavouritesViewModel(get()) }
}