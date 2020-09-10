package com.sd.app.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.sd.app.BuildConfig
import com.sd.app.data.network.ApiService
import com.sd.app.utils.ConnectivityUtil
import com.sd.app.utils.NetworkConnectionInterceptor
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit


private const val TIMEOUT_IN_SECS = 30
private const val BASE_URL = "https://www.flickr.com/services/"


val networkModule = module {

    fun provideApiService(
        okHttpClient: OkHttpClient,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        gson: Gson
    ): ApiService? {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(okHttpClient)
            .build().create(ApiService::class.java)
    }


    fun provideCookieManager(): CookieManager? {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        CookieHandler.setDefault(cookieManager)
        return cookieManager
    }


    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        connectionSpec: ConnectionSpec
    ): OkHttpClient? {
        val httpClient = OkHttpClient.Builder()
       // httpClient.addInterceptor(headerAuthorizationInterceptor)
        httpClient.addInterceptor(networkConnectionInterceptor)
        httpClient.connectTimeout(
            TIMEOUT_IN_SECS.toLong(),
            TimeUnit.SECONDS
        )
        httpClient.readTimeout(
            TIMEOUT_IN_SECS.toLong(),
            TimeUnit.SECONDS
        )
       // httpClient.cookieJar(cookieJar)
       // httpClient.cache(cache)
        httpClient.connectionSpecs(listOf(ConnectionSpec.CLEARTEXT, connectionSpec))
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(loggingInterceptor)
        }
        return httpClient.build()
    }

    fun provideConnectionSpecs() = ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
        .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
        .allEnabledCipherSuites()
        .build()


    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }


    /* fun provideHeaderAuthorizationInterceptor(): Interceptor? {
         return Interceptor { chain: Interceptor.Chain ->
             var request = chain.request()
             val requestBuilder = request.newBuilder()
             request = requestBuilder.build()
             val response = chain.proceed(request)
             if (response.code() == 401) {
                 //return response
             }
             response
         }
     }*/

    fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .setLenient()
            .create()
    }


    /*  fun provideCookieJar(context: Application?): CookieJar {
          return PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
      }*/


    /* fun provideCache(context: Application): Cache {
         val cacheSize = 5 * 1024 * 1024 // 5 MB
         val cacheDir = context.cacheDir
         return Cache(cacheDir, cacheSize.toLong())
     }*/


    fun provideRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory? {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    fun provideNetworkConnectionInterceptor(connectivityUtil: ConnectivityUtil): NetworkConnectionInterceptor? {
        return NetworkConnectionInterceptor(connectivityUtil)
    }



    single { provideNetworkConnectionInterceptor(get()) }
    single { provideRxJavaCallAdapterFactory() }
    // single { provideCache(get()) }
    // single { provideCookieJar(get()) }
    single { provideGson() }
    // single { provideHeaderAuthorizationInterceptor(get()) }
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(get(), get(), get()) }
    single { provideApiService(get(), get(), get()) }
    single { provideConnectionSpecs() }

}


