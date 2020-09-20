package com.shriharsh.videocarousel.presentation.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.shriharsh.videocarousel.R
import com.shriharsh.videocarousel.domain.model.VideoMedia
import com.shriharsh.videocarousel.presentation.viewmodel.VideoCarouselViewModel
import com.shriharsh.videocarousel.utils.STORAGE_REQUEST_CODE
import com.shriharsh.videocarousel.utils.exoplayerutils.ExoPlayerViewAdapter
import com.shriharsh.videocarousel.utils.exoplayerutils.RecyclerViewScrollListener
import com.shriharsh.videocarousel.utils.isStoragePermissionGranted
import com.shriharsh.videocarousel.utils.requestStoragePermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

@AndroidEntryPoint
class VideosCarouselActivity : AppCompatActivity(), VideosAdapter.OnFavoriteClickListener {

    private val videoCarouselViewModel by viewModels<VideoCarouselViewModel>()
    private lateinit var scrollListener: RecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        observeFetchedVideos()
        fetchAllVideos()
    }

    override fun onPause() {
        super.onPause()
        ExoPlayerViewAdapter.releaseAllPlayers()
    }

    private fun observeFetchedVideos() {
        videoCarouselViewModel.observeFetchedVideos().observe(this, Observer { result ->
            renderCarousel(result)
        })
    }

    private fun renderCarousel(result: List<VideoMedia>?) {
        if (result.isNullOrEmpty()) {
            hideVideosRecyclerView()
            showEmptyVideoContent()
        } else {
            hideEmptyVideoContent()
            showSwipeGestureView()
            showVideosRecyclerView(result)
        }
    }

    private fun showSwipeGestureView() {
        cl_swipe_layout.visibility = VISIBLE
    }

    private fun showVideosRecyclerView(result: List<VideoMedia>) {
        rv_videos_list.visibility = VISIBLE
        val videoAdapter = VideosAdapter(this, result, this)
        rv_videos_list.adapter = videoAdapter
    }

    private fun hideEmptyVideoContent() {
        tv_empty_video.visibility = GONE
    }

    private fun showEmptyVideoContent() {
        tv_empty_video.visibility = VISIBLE
    }

    private fun hideVideosRecyclerView() {
        rv_videos_list.visibility = GONE
    }

    private fun setupUI() {
        setRecyclerView()
        cl_swipe_layout.setOnClickListener {
            cl_swipe_layout.visibility = GONE
        }
    }

    private fun setRecyclerView() {
        rv_videos_list.layoutManager = LinearLayoutManager(this)
        PagerSnapHelper().apply {
            this.attachToRecyclerView(rv_videos_list)
        }
        scrollListener = object : RecyclerViewScrollListener() {
            override fun onItemIsFirstVisibleItem(index: Int) {
                if (index != -1)
                    ExoPlayerViewAdapter.playIndexThenPausePreviousPlayer(index)
            }

        }
        rv_videos_list.addOnScrollListener(scrollListener)
    }

    private fun fetchAllVideos() {
        if (isStoragePermissionGranted()) {
            videoCarouselViewModel.fetchAllVideos()
        } else {
            requestStoragePermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            STORAGE_REQUEST_CODE -> {
                if (grantResults.isEmpty() ||
                    grantResults[0] != PackageManager.PERMISSION_GRANTED
                ) {
                    Timber.e("Permission Denied")
                    hideVideosRecyclerView()
                    showEmptyVideoContent()
                    tv_empty_video.text = "Please go to setting and provide storage permission to the app!"
                } else {
                    Timber.e("Permission Granted")
                    videoCarouselViewModel.fetchAllVideos()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onFavoriteClick(view: View?, position: Int, videoMedia: VideoMedia) {
        videoCarouselViewModel.favoriteVideo(videoMedia)
    }

}