package com.example.unsplashphotos.utils

interface EntityMapper <RemoteEntity, DomainModel>{

    fun mapFromEntity(entity: RemoteEntity): DomainModel

    fun mapToEntity(domainModel: DomainModel): RemoteEntity
}
