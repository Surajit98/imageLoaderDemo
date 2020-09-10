package com.sd.app.app

import android.app.Application
import com.sd.app.di.appModule
import com.sd.app.di.networkModule
import com.sd.app.di.repositoryModule
import com.sd.app.di.viewModelModule

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class PhotosApp : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@PhotosApp)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }


        Timber.plant(Timber.DebugTree())

    }

    companion object {
        val TAG = PhotosApp::class.java.simpleName

    }
}