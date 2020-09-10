package com.sd.app.data.network


import com.sd.app.BuildConfig
import com.sd.app.data.network.network_responses.PhotosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("rest/?method=flickr.photos.search&api_key=" + BuildConfig.FLICKER_API_KEY + "&format=json&nojsoncallback=1&extras=url_m")
    suspend fun fetchPhotos(
        @Query("text") text: String,
        @Query("page") page: Int
    ): Response<PhotosResponse>


}