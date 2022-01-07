package com.example.unsplashphotos.domain.model

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

    data class Urls(
        val regular: String,
        val thumb: String
    ) {
        companion object {
            val EMPTY = Urls("", "")
        }
    }
}

