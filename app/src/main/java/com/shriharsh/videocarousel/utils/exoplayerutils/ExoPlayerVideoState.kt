package com.shriharsh.videocarousel.utils.exoplayerutils

import com.google.android.exoplayer2.Player

/**
 * Created on 20/09/20.
 * shriharsh
 */
interface ExoPlayerVideoState {
    fun onStartedPlaying(player: Player)

    fun onFinishedPlaying(player: Player)
}