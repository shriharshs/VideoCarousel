package com.shriharsh.videocarousel.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shriharsh.videocarousel.domain.VideosRepository
import com.shriharsh.videocarousel.domain.model.VideoMedia
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created on 19/09/20.
 * shriharsh
 */
class VideoCarouselViewModel @ViewModelInject constructor(
    private val videosRepository: VideosRepository
) : ViewModel() {

    private var videosListLiveDate = MutableLiveData<List<VideoMedia>>()

    fun fetchAllVideos() {
        viewModelScope.launch {
          val result = videosRepository.fetchAllVideos()

           Timber.e("Result of all media files - ${result.size}")

            videosListLiveDate.postValue(result)
        }
    }

    fun observeFetchedVideos(): LiveData<List<VideoMedia>>{
        return videosListLiveDate
    }

    fun favoriteVideo(videoMedia: VideoMedia) {
        viewModelScope.launch {
            videosRepository.favoriteVideo(videoMedia)
        }
    }

}