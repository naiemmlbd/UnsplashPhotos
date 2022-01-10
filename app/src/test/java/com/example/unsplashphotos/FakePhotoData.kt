package com.example.unsplashphotos

import com.example.unsplashphotos.domain.model.Photo


fun getPhotoList(): MutableList<Photo> {
    return mutableListOf(
        Photo("aa", "photoAA", Photo.Urls("", ""), Photo.Links("", "", "", ""), 10),
        Photo("bb", "photoBB", Photo.Urls("", ""), Photo.Links("", "", "", ""), 20)
    )
}
