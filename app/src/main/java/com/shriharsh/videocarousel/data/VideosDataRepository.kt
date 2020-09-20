package com.shriharsh.videocarousel.data

import com.shriharsh.videocarousel.data.local.source.VideosLocalDataSource
import com.shriharsh.videocarousel.data.storage.StorageDataSource
import com.shriharsh.videocarousel.domain.VideosRepository
import com.shriharsh.videocarousel.domain.model.VideoMedia
import javax.inject.Inject

/**
 * Created on 19/09/20.
 * shriharsh
 */
class VideosDataRepository @Inject constructor(
    private val storageDataSource: StorageDataSource,
    private val localDataSource: VideosLocalDataSource
): VideosRepository {

    override suspend fun fetchAllVideos(): List<VideoMedia> {
        val fetchedVideos = storageDataSource.fetchAllVideos()

        if (!fetchedVideos.isNullOrEmpty()){
            localDataSource.saveAllVideos(fetchedVideos)

            return localDataSource.getAllVideos()
        }

        return listOf()
    }

    override suspend fun favoriteVideo(videoMedia: VideoMedia) {
        localDataSource.bookmarkVideo(videoMedia)
    }
}