package com.sd.app.di

import com.sd.app.data.data_source.PhotosDataSource
import com.sd.app.data.network.ApiService
import com.sd.app.data.repositories.PhotosRepository

import org.koin.dsl.module

val repositoryModule = module {

    //flicker photo data
    fun providePhotosDataSource(api: ApiService) = PhotosDataSource(api)
    fun providePhotosRepository(dataSource: PhotosDataSource) =
        PhotosRepository(
            dataSource

        )

    factory { providePhotosDataSource(get()) }
    factory { providePhotosRepository(get()) }


}