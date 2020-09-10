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
    var searchText = MutableLiveData<String>()
    var nextPage = MutableLiveData<Boolean>()
    var pages: Int = 0
    var currentPage: Int = 0
    var fetchNextPage: Boolean = false
    var endOfPage = MutableLiveData<Boolean>()


    fun fetchImages() = if (searchText.value.isNullOrEmpty()) null else Transformations.map(
        repository.fetchImages(
            searchText.value ?: "",
            currentPage
        )
    ) {
        when (it.status) {
            ApiResult.Status.LOADING -> {
                isLoading.value = true
                null
            }
            ApiResult.Status.ERROR -> {
                showMessage.value = it.message
                isLoading.value = false
                null
            }
            else -> {
                isLoading.value = false
                if (fetchNextPage) {
                    photosData.value?.addAll(it.data!!.photos.photo)
                } else {
                    photosData.value = it.data!!.photos.photo
                }
                null

            }
        }


    }

    fun textChanged() = Transformations.switchMap(searchText) {
        if (it.isNullOrEmpty()) {
            null
        } else {
            fetchNextPage = false
            fetchImages()
        }
    }

    fun nextPage() = Transformations.switchMap(nextPage) {
        fetchNextPage = true
        when {
            searchText.value.isNullOrEmpty() -> {
                null
            }
            pages < currentPage -> {
                fetchImages()
            }
            else -> {
                endOfPage.value = true
                null
            }
        }
    }
}