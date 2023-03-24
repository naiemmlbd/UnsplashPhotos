package com.example.unsplashphotos.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.core.content.FileProvider.getUriForFile
import com.example.unsplashphotos.R
import java.io.File
import java.io.FileOutputStream


object ShareUtils {

    //To share image from the imageView
    fun shareImage(context: Context, photoId: String, imageViewBitmapDrawable: BitmapDrawable) {
        val savedInFile = saveToCache(context, photoId, imageViewBitmapDrawable)
        if (savedInFile != null) {
            val share = Intent(Intent.ACTION_SEND)
            share.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            share.type = "image/*"
            val uri: Uri = getUriForFile(
                context, context.applicationContext.packageName + ".provider", savedInFile
            )
            share.putExtra(Intent.EXTRA_STREAM, uri)
            context.startActivity(
                Intent.createChooser(
                    share,
                    context.getString(R.string.sharevia)
                )
            )
        }
    }

    private fun saveToCache(
        context: Context,
        photoId: String,
        imageViewBitmapDrawable: BitmapDrawable
    ): File? {
        var saveStatus = false
        val bitmap = imageViewBitmapDrawable.bitmap
        val directory = context.cacheDir
        val fileName = "$photoId.jpeg"
        val outputFile = File(directory, fileName)
        try {
            val outputStream = FileOutputStream(outputFile)
            saveStatus = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return if (saveStatus) outputFile else null
    }

}
