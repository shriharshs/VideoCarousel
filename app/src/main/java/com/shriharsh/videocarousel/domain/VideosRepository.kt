package com.shriharsh.videocarousel.domain

import com.shriharsh.videocarousel.domain.model.VideoMedia

/**
 * Created on 19/09/20.
 * shriharsh
 */
interface VideosRepository {
    suspend fun fetchAllVideos(): List<VideoMedia>
    suspend fun favoriteVideo(videoMedia: VideoMedia)
}