package com.shriharsh.videocarousel.data.local.source

import com.shriharsh.videocarousel.data.local.model.VideosDao
import com.shriharsh.videocarousel.domain.model.VideoMedia
import javax.inject.Inject

/**
 * Created on 19/09/20.
 * shriharsh
 */
class VideosLocalDataSource @Inject constructor(private val videosDao: VideosDao) {

    suspend fun getAllVideos(): List<VideoMedia> {
        return videosDao.getAllVideos()
    }

    suspend fun saveAllVideos(videosList: List<VideoMedia>) {
        videosDao.saveVideos(videosList)
    }

    suspend fun bookmarkVideo(videoMedia: VideoMedia) {
        videosDao.bookmarkVideo(videoMedia)
    }

}