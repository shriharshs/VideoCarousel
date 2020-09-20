package com.shriharsh.videocarousel.utils.exoplayerutils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created on 20/09/20.
 * shriharsh
 */
abstract class RecyclerViewScrollListener : RecyclerView.OnScrollListener() {
    private var firstVisibleItem = 0
    private var visibleItemCount = 0

    @Volatile
    private var mEnabled = true
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (mEnabled) {
            val manager = recyclerView.layoutManager
            require(manager is LinearLayoutManager) { "Recyclerview with LinearLayoutManager is needed" }
            visibleItemCount = manager.childCount
            firstVisibleItem = manager.findFirstCompletelyVisibleItemPosition()
            onItemIsFirstVisibleItem(firstVisibleItem)
        }
    }

    abstract fun onItemIsFirstVisibleItem(index: Int)
}