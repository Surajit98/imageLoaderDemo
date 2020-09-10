package com.sd.app.ui.photo_activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sd.app.data.model.Photo
import com.sd.app.data.network.ApiResult
import com.sd.app.data.repositories.PhotosRepository

class PhotosViewModel(
    private val repository: PhotosRepository
) : ViewModel() {
    var isLoading = MutableLiveData<Boolean>()
    var showMessage = MutableLiveData<String>()
    var photosData = MutableLiveData<ArrayList<Photo>>()
    var fetch = MutableLiveData<Boolean>()
    var searchText: String? = null
    var pages: Int = 0
    var currentPage: Int = 0
    var fetchNextPage = MutableLiveData<Boolean>().apply { value = false }


    fun fetchImages1() = Transformations.switchMap(fetch) {
        Transformations.map(
            repository.fetchImages(
                searchText ?: "",
                currentPage
            )
        ) {
            when (it.status) {
                ApiResult.Status.LOADING -> {
                    isLoading.value = true
                }
                ApiResult.Status.ERROR -> {
                    showMessage.value = it.message
                    isLoading.value = false
                }
                else -> {
                    isLoading.value = false
                    /* if (fetchNextPage.value == true) {
                         photosData.value?.addAll(it.data!!.photos.photo)
                     } else {
                         if(it.data?.photos?.photo.isNullOrEmpty()){
                             showMessage.value="No results found"
                         }else {
                             photosData.value = it.data!!.photos.photo
                         }
                     }*/
                    pages = it.data?.photos?.pages ?: 0
                    if (it.data?.photos?.photo.isNullOrEmpty()) {
                        showMessage.value = "No results found"
                    } else {
                        photosData.value = it.data!!.photos.photo
                    }

                }
            }
        }

    }

    fun searchData(text: String) {
        photosData.value=null
        searchText = text
        currentPage = 1
        photosData.value?.clear()
        fetch.value = true
        fetchNextPage.value = false
    }

    fun loadNext() {
        if (isLastPage())
            return
        currentPage++
        fetch.value = true
        fetchNextPage.value = true

    }

    fun isLastPage() = currentPage == pages


}