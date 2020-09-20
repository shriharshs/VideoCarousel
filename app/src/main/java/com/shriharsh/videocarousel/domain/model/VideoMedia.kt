package com.shriharsh.videocarousel.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created on 19/09/20.
 * shriharsh
 */

@Entity
data class VideoMedia(
    @PrimaryKey
    var mediaUrl: String,
    var isFavorite: Int = 0
)