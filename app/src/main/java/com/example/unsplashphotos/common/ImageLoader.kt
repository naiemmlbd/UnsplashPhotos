package com.example.unsplashphotos.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide


class ImageLoader {


    fun load(
        context: Context,
        url: String,
        imageView: ImageView
    ) {
        Glide.with(context)
            .load(url)
            .fitCenter()
            .into(imageView)
    }


    fun load(
        context: Context,
        bitmap: Bitmap,
        imageView: ImageView,
        placeholder: Int
    ) {
        Glide.with(context)
            .load(bitmap)
            .placeholder(placeholder)
            .into(imageView)
    }

    fun load(
        context: Context,
        drawable: Int,
        imageView: ImageView
    ) {
        Glide.with(context)
            .load(drawable)
            .fitCenter()
            .into(imageView)
    }

    fun load(
        context: Context,
        drawable: Drawable,
        imageView: ImageView
    ) {
        Glide.with(context)
            .load(drawable)
            .into(imageView)
    }

    companion object {
        protected var INSTANCE: ImageLoader? = null
        val instance: ImageLoader?
            get() {
                if (INSTANCE == null) INSTANCE =
                    ImageLoader()
                return INSTANCE
            }
    }
}