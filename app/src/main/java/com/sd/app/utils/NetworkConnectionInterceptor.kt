package com.sd.app.utils


import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class NetworkConnectionInterceptor(val connectivityManager: ConnectivityUtil) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!connectivityManager.isNetworkAvailable()) {
            throw ErrorHandler.NoInternetException()
        }
        //  val builder = chain.request().newBuilder()
        return chain.proceed(chain.request())
    }

}