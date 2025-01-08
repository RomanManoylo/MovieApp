package com.example.saveshare.di

import androidx.room.Room
import com.example.saveshare.data.local.AppDatabase
import com.example.saveshare.data.local.ILocalDataSource
import com.example.saveshare.data.local.LocalDataSource
import com.example.saveshare.data.local.VideoDao
import com.example.saveshare.data.remote.IRemoteDataSource
import com.example.saveshare.data.remote.RemoteDataSource
import com.example.saveshare.data.repository.VideosRepositoryImpl
import com.example.saveshare.domain.repository.VideoRepository
import com.example.saveshare.util.AppConstants
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, AppConstants.Database.NAME)
            .build()
    }
    single<ILocalDataSource> {
        LocalDataSource(get())
    }
    single<IRemoteDataSource> {
        RemoteDataSource(get())
    }
    single<VideoRepository> { VideosRepositoryImpl(get(), get()) }
    single<VideoDao> {
        val database = get<AppDatabase>()
        database.getVideoDao()
    }
}