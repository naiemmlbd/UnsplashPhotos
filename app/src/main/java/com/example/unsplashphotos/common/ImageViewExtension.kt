package com.example.unsplashphotos.common


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("loadUrl")
fun ImageView.loadUrl(url: String?) {

    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .into(this)
}
