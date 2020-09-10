package com.sd.app.data.network.network_responses

import com.sd.app.data.model.Photos

data class PhotosResponse(
    val photos: Photos,
    val stat: String
)