package com.example.unsplashphotos

import com.example.unsplashphotos.data.model.Links
import com.example.unsplashphotos.data.model.Photo
import com.example.unsplashphotos.data.model.Urls


fun getPhotoList(): MutableList<Photo>{
    return mutableListOf(
        Photo("aa","photoAA", Urls("","","","",""), Links("","","","")),
        Photo("bb","photoBB", Urls("","","","",""), Links("","","","")),
        Photo("cc","photoCC", Urls("","","","",""), Links("","","","")),
        Photo("dd","photoDD", Urls("","","","",""), Links("","","",""))
    )
}
