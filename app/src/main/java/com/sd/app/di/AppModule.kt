package com.sd.app.di

import android.content.Context
import com.jakewharton.picasso.OkHttp3Downloader
import com.sd.app.utils.ConnectivityUtil
import com.squareup.picasso.Picasso

import okhttp3.OkHttpClient
import org.koin.dsl.module


var appModule = module {


    fun provideConnectivityUtil(context: Context) = ConnectivityUtil(context)


    fun getPicasso(context: Context): Picasso {
        return Picasso.Builder(context)
            .loggingEnabled(true)
            .build()

    }

    single { provideConnectivityUtil(get()) }
    single { getPicasso(get()) }

}