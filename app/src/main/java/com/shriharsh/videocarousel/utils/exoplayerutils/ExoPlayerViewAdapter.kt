package com.shriharsh.videocarousel.utils.exoplayerutils

import android.net.Uri
import androidx.databinding.BindingAdapter
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

/**
 * Created on 20/09/20.
 * shriharsh
 */
class ExoPlayerViewAdapter {

    companion object {

        private var playersMap: MutableMap<Int, SimpleExoPlayer> = mutableMapOf()
        private var currentPlayingVideo: Pair<Int, SimpleExoPlayer>? = null

        fun releaseAllPlayers() {
            playersMap.map {
                it.value.release()
            }
        }

        fun releaseRecycledViewPlayer(index: Int) {
            playersMap[index]?.release()
        }

        private fun pauseCurrentPlayingVideo() {
            if (currentPlayingVideo != null) {
                currentPlayingVideo?.second?.playWhenReady = false
            }
        }

        fun playIndexThenPausePreviousPlayer(index: Int) {
            if (playersMap[index]?.playWhenReady == false) {
                pauseCurrentPlayingVideo()
                playersMap[index]?.playWhenReady = true
                currentPlayingVideo = Pair(index, playersMap.get(index)!!)
            }

        }


        @JvmStatic
        @BindingAdapter(value = ["media_url", "exo_player_state", "video_index"], requireAll = false)
        fun PlayerView.loadVideo(mediaUrl: String, callback: ExoPlayerVideoState, videoIndex: Int? = null) {
            val player = SimpleExoPlayer.Builder(context).build()
            player.apply {
                playWhenReady = false
                repeatMode = Player.REPEAT_MODE_ALL
                setKeepContentOnPlayerReset(true)
                useController = false
            }
            val mediaSource =
                ProgressiveMediaSource.Factory(DefaultDataSourceFactory(context, "MY_USER_AGENT"))
                    .createMediaSource(Uri.parse(mediaUrl))
            player.prepare(mediaSource)
            this.player = player

            if (playersMap.containsKey(videoIndex))
                playersMap.remove(videoIndex)

            if (videoIndex != null)
                playersMap[videoIndex] = player

            this.player?.addListener(object : Player.EventListener {
                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    super.onPlayerStateChanged(playWhenReady, playbackState)

                    if (playbackState == Player.STATE_READY && player.playWhenReady) {
                        callback.onStartedPlaying(player)
                    }
                }
            })
        }
    }
}
