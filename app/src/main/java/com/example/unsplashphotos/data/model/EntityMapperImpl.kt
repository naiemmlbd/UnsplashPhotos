package com.example.unsplashphotos.data.model

import com.example.unsplashphotos.data.model.local.Photo
import com.example.unsplashphotos.utils.EntityMapper
import javax.inject.Inject

class EntityMapperImpl @Inject constructor() : EntityMapper<PhotoRemoteEntity, Photo> {

    override fun mapFromEntity(entity: PhotoRemoteEntity): Photo {
        return Photo(
            id = entity.id ?: "",
            altDescription = entity.altDescription ?: "",
            urls = entity.urls ?: Urls(),
            links = entity.links ?: Links(),
            likes = entity.likes ?: 0
        )
    }

    override fun mapToEntity(domainModel: Photo): PhotoRemoteEntity {
        return PhotoRemoteEntity(
            id = domainModel.id,
            altDescription = domainModel.altDescription,
            urls = domainModel.urls,
            links = domainModel.links,
            likes = domainModel.likes
        )
    }

    fun mapFromEntityList(entities: List<PhotoRemoteEntity>): List<Photo> {
        return entities.map { mapFromEntity(it) }
    }

}
