package com.example.unsplashphotos.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val id: String,
    val altDescription: String,
    val urls: Urls,
    val links: Links,
    val likes: Int,
) : Parcelable {

    @Parcelize
    data class Links(
        val download: String,
        val downloadLocation: String,
        val html: String,
        val self: String,
    ) : Parcelable {
        companion object {
            val EMPTY = Links("", "", "", "")
        }
    }

    @Parcelize
    data class Urls(
        val regular: String,
        val thumb: String,
    ) : Parcelable {
        companion object {
            val EMPTY = Urls("", "")
        }
    }
}
