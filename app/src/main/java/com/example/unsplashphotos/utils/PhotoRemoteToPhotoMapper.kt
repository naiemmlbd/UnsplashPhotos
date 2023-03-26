package com.example.unsplashphotos.utils

interface PhotoRemoteToPhotoMapper<RemoteEntity, DomainModel> {

    fun mapFromEntity(entity: RemoteEntity): DomainModel

    fun mapToEntity(domainModel: DomainModel): RemoteEntity
}
