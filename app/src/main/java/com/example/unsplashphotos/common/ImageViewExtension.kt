package com.example.unsplashphotos.common


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.unsplashphotos.common.ImageLoader


@BindingAdapter("loadUrl")
fun ImageView.loadUrl(url: String?) {
    ImageLoader.instance?.load(context, url ?: "", this)
}