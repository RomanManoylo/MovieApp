package com.example.saveshare.di

import com.example.saveshare.domain.usecases.IVideoUseCase
import com.example.saveshare.domain.usecases.VideoUseCase
import org.koin.dsl.module

val domainModule = module {
    single<IVideoUseCase> {
        VideoUseCase(
            get()
        )
    }
}