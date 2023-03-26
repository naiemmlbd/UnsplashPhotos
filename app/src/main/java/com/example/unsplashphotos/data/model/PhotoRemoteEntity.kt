package com.example.unsplashphotos.data.model

import com.google.gson.annotations.SerializedName

data class PhotoRemoteEntity(
    @SerializedName("id")
    val id: String?,
    @SerializedName("alt_description")
    val altDescription: String?,
    @SerializedName("urls")
    val urlsRemote: UrlsRemote?,
    @SerializedName("links")
    val linksRemote: LinksRemote?,
    @SerializedName("likes")
    val likes: Int?,
)
