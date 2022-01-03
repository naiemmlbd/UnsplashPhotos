package com.example.unsplashphotos.data.model.domain

import com.example.unsplashphotos.data.model.Links
import com.example.unsplashphotos.data.model.Urls

data class Photo(
    val id: String,
    val altDescription: String,
    val urls: Urls,
    val links: Links,
    val likes: Int
)

