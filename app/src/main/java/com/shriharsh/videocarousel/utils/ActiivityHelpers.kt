package com.shriharsh.videocarousel.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.shriharsh.videocarousel.presentation.ui.VideosCarouselActivity

/**
 * Created on 19/09/20.
 * shriharsh
 */

const val STORAGE_REQUEST_CODE = 101

fun VideosCarouselActivity.requestStoragePermission() {
    val permission = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    if (permission != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Please provide storage access for this app to use.")
                .setTitle("Storage Permission required")

            builder.setPositiveButton(
                "OK"
            ) { _, _ ->
                makeRequest()
            }

            val dialog = builder.create()
            dialog.show()
        } else {
            makeRequest()
        }
    }
}

fun VideosCarouselActivity.makeRequest() {
    ActivityCompat.requestPermissions(
        this,
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
        STORAGE_REQUEST_CODE
    )
}

fun VideosCarouselActivity.isStoragePermissionGranted(): Boolean {
    return (PermissionChecker.checkSelfPermission(
        this,
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PermissionChecker.PERMISSION_GRANTED)
}