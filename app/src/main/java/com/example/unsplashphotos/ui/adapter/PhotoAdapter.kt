package com.example.unsplashphotos.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashphotos.R
import com.example.unsplashphotos.data.model.Photo
import com.example.unsplashphotos.databinding.ItemPhotoBinding


class PhotoAdapter() : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private val photoList = ArrayList<Photo>()

    fun setList(photos: List<Photo>) {
        photoList.clear()
        photoList.addAll(photos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemPhotoBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_photo, parent, false)

        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.photo = photo
        }

    }
}