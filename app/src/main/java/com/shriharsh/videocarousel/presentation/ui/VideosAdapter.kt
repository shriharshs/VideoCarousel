package com.shriharsh.videocarousel.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.Player
import com.shriharsh.videocarousel.R
import com.shriharsh.videocarousel.databinding.ItemVideoMediaBinding
import com.shriharsh.videocarousel.domain.model.VideoMedia
import com.shriharsh.videocarousel.utils.exoplayerutils.ExoPlayerVideoState
import com.shriharsh.videocarousel.utils.exoplayerutils.ExoPlayerViewAdapter

/**
 * Created on 20/09/20.
 * shriharsh
 */
class VideosAdapter(
    val context: Context,
    private var videosList: List<VideoMedia>,
    private var onFavoriteClickListener: OnFavoriteClickListener
) : RecyclerView.Adapter<VideosAdapter.ViewHolder>(), ExoPlayerVideoState {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosAdapter.ViewHolder {
        val binding: ItemVideoMediaBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context)
            , R.layout.item_video_media, parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = videosList.size

    override fun onBindViewHolder(holder: VideosAdapter.ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onViewRecycled(holder: ViewHolder) {
        val position = holder.adapterPosition
        ExoPlayerViewAdapter.releaseRecycledViewPlayer(position)
        super.onViewRecycled(holder)
    }

    private fun getItem(position: Int): VideoMedia {
        return videosList[position]
    }

    interface OnFavoriteClickListener {
        fun onFavoriteClick(
            view: View?,
            position: Int,
            videoMedia: VideoMedia
        )
    }

    inner class ViewHolder(private val binding: ItemVideoMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: VideoMedia) {
            binding.bookmark.setOnClickListener {
                videosList[adapterPosition].isFavorite = if (model.isFavorite == 1) 0 else 1
                isFavorited(model, binding)
                onFavoriteClickListener.onFavoriteClick(it, adapterPosition, videosList[adapterPosition])
            }
            binding.apply {
                videoMedia = model
                exoPlayerState = this@VideosAdapter
                videoPosition = adapterPosition
                isFavorited(model, binding)
                executePendingBindings()
            }
        }

        private fun isFavorited(details: VideoMedia, binding: ItemVideoMediaBinding) {
            if (details.isFavorite == 1) {
                binding.bookmark.setImageResource(R.drawable.ic_favorite_filled)
            } else {
                binding.bookmark.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }

    override fun onStartedPlaying(player: Player) {
    }

    override fun onFinishedPlaying(player: Player) {
    }
}