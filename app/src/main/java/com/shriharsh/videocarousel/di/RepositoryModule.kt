package com.shriharsh.videocarousel.di

import android.content.ContentResolver
import android.content.Context
import com.shriharsh.videocarousel.data.VideosDataRepository
import com.shriharsh.videocarousel.data.local.model.VideosDao
import com.shriharsh.videocarousel.data.local.source.VideosLocalDataSource
import com.shriharsh.videocarousel.data.storage.StorageDataSource
import com.shriharsh.videocarousel.domain.VideosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Created on 19/09/20.
 * shriharsh
 */

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    fun providesStorageSource(contentResolver: ContentResolver) =
        StorageDataSource(contentResolver)

    @Provides
    fun providesVideosLocalSource(videosDao: VideosDao) =
        VideosLocalDataSource(videosDao)

    @Provides
    fun providesVideosDataRepository(
        storageDataSource: StorageDataSource,
        localDataSource: VideosLocalDataSource
    ): VideosRepository =
        VideosDataRepository(
            storageDataSource, localDataSource
        )

}