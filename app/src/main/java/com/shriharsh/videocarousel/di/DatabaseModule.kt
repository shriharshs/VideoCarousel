package com.shriharsh.videocarousel.di

import android.content.Context
import androidx.room.Room
import com.shriharsh.videocarousel.data.local.source.VideosDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesVideosDb(@ApplicationContext context: Context): VideosDB {
        return Room.databaseBuilder(
            context,
            VideosDB::class.java,
            "videos_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providesVideosDao(videosDB: VideosDB) = videosDB.videosDao()

}