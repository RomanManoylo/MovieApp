package com.example.saveshare.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.saveshare.data.models.Video

@Database(entities = [Video::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getVideoDao(): VideoDao
}