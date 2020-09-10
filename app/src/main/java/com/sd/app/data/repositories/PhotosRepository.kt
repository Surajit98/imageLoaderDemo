package com.sd.app.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.sd.app.data.data_source.PhotosDataSource
import com.sd.app.data.network.ApiResult
import com.sd.app.data.network.network_responses.PhotosResponse
import kotlinx.coroutines.Dispatchers

class PhotosRepository(
    private val dataSource: PhotosDataSource
) {


    fun fetchImages(text: String, page: Int): LiveData<ApiResult<PhotosResponse>> =
        liveData(Dispatchers.IO) {
            emit(ApiResult.loading())
            val response = dataSource.fetchPhotos(text, page)
            if (response.status == ApiResult.Status.SUCCESS) {
                if (response.data?.stat == "ok") {
                    emit(ApiResult.success(response.data))
                } else if (response.status == ApiResult.Status.ERROR) {
                    emit(ApiResult.error(response.message!!))

                }

            } else {
                emit(ApiResult.error(response.message!!, null))

            }
        }
}