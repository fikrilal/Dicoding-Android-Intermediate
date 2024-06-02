package com.fikrilal.narate_mobile_apps.story.data.utils

import android.content.Context
import android.content.ContentValues
import android.net.Uri
import android.provider.MediaStore

object ImagePickerUtils {
    fun createImageUri(context: Context): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, "New Photo")
            put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        }
        return context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
        )
    }
}
