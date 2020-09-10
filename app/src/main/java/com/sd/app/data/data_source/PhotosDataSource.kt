package com.sd.app.data.data_source

import com.sd.app.data.network.ApiService


class PhotosDataSource(val apiService: ApiService) : BaseDataSource() {

    suspend fun fetchPhotos(text: String,page:Int) = getResult { apiService.fetchPhotos(text,page) }



}