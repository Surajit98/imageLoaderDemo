package com.sd.app.di

import android.content.Context
import com.jakewharton.picasso.OkHttp3Downloader
import com.sd.app.utils.ConnectivityUtil
import com.squareup.picasso.Picasso

import okhttp3.OkHttpClient
import org.koin.dsl.module


var appModule = module {


    fun provideConnectivityUtil(context: Context) = ConnectivityUtil(context)

    fun getPicassoDownloader(okHttpClient: OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(okHttpClient)
    }

    fun getPicasso(context: Context, downloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(context)
            .downloader(downloader)
            .indicatorsEnabled(true)
            .loggingEnabled(true)
            .build()

    }

    single { provideConnectivityUtil(get()) }
    single { getPicassoDownloader(get()) }
    single { getPicasso(get(), get()) }

}