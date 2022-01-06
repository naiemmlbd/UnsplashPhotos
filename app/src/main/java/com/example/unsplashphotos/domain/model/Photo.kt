package com.example.unsplashphotos.domain.model

import com.example.unsplashphotos.data.model.Urls

data class Photo(
    val id: String,
    val altDescription: String,
    val urls: Urls,
    val links: Links,
    val likes: Int
) {
    
    data class Links(
        val download: String,
        val downloadLocation: String,
        val html: String,
        val self: String
    ) {
        companion object {
            val EMPTY = Links("", "", "", "")
        }
    }
}

