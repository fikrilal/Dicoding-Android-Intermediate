package com.fikrilal.narate_mobile_apps.story.data.utils

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import kotlin.math.sqrt

object CompressImageUtils {
    fun uriToBitmap(contentResolver: ContentResolver, imageUri: Uri): Bitmap {
        val inputStream = contentResolver.openInputStream(imageUri)
        return BitmapFactory.decodeStream(inputStream)
    }

    fun compressBitmap(original: Bitmap, quality: Int): ByteArray {
        val outputStream = ByteArrayOutputStream()
        original.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        return outputStream.toByteArray()
    }

    fun resizeBitmap(source: Bitmap, maxLength: Int): Bitmap {
        val ratioSquare = source.width.toDouble() * source.height.toDouble() / (maxLength * maxLength)
        if (ratioSquare <= 1) return source
        val scaleFactor = sqrt(ratioSquare)
        val newWidth = (source.width / scaleFactor).toInt()
        val newHeight = (source.height / scaleFactor).toInt()
        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true)
    }
}