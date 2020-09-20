package com.shriharsh.videocarousel.data.storage

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.shriharsh.videocarousel.domain.model.VideoMedia
import javax.inject.Inject

/**
 * Created on 19/09/20.
 * shriharsh
 */
class StorageDataSource @Inject constructor(private val contentResolver: ContentResolver) {

    fun fetchAllVideos(): List<VideoMedia> {
        val videoList: MutableList<VideoMedia> = mutableListOf()
        val uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val videoUri =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                val videoModel = VideoMedia(mediaUrl = videoUri)
                videoList.add(videoModel)
            } while (cursor.moveToNext())
        }

        return videoList.toList()
    }

}