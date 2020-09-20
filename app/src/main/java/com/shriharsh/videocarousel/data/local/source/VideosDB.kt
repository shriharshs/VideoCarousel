package com.shriharsh.videocarousel.data.local.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shriharsh.videocarousel.data.local.model.VideosDao
import com.shriharsh.videocarousel.domain.model.VideoMedia

@Database(entities = [VideoMedia::class], version = 1, exportSchema = false)
abstract class VideosDB: RoomDatabase() {
    abstract fun videosDao(): VideosDao
}