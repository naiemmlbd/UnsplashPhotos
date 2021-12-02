package com.example.unsplashphotos.data.model


import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("id")
    val id: String,
    @SerializedName("alt_description")
    val altDescription: String,
    @SerializedName("urls")
    val urls: Urls

)