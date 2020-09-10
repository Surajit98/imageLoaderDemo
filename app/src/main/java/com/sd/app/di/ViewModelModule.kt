package com.sd.app.di


import com.sd.app.ui.photo_activity.PhotosViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { PhotosViewModel(get()) }

}