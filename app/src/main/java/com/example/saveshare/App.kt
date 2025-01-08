package com.example.saveshare

import android.app.Application
import com.example.saveshare.di.dataModule
import com.example.saveshare.di.domainModule
import com.example.saveshare.di.networkModule
import com.example.saveshare.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(networkModule, domainModule, dataModule, presentationModule)
        }
    }
}