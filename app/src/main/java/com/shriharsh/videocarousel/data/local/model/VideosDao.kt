package com.shriharsh.videocarousel.data.local.model

import androidx.room.*
import com.shriharsh.videocarousel.domain.model.VideoMedia

@Dao
interface VideosDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveVideos(list: List<VideoMedia>)

    @Query("SELECT * FROM VideoMedia")
    suspend fun getAllVideos(): List<VideoMedia>

    @Update
    suspend fun bookmarkVideo(videoMedia: VideoMedia)

}