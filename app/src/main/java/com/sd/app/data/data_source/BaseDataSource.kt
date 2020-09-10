package com.sd.app.data.data_source

import com.sd.app.data.network.ApiResult
import com.sd.app.utils.ErrorHandler
import retrofit2.Response
import timber.log.Timber
import java.net.SocketTimeoutException

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ApiResult<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return ApiResult.success(body)
                }
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return if (e is SocketTimeoutException) {
                errorMsg(ErrorHandler.SocketTimeOutException())
            } else {
                errorMsg(e)
            }
        }
    }


    private fun <T> error(message: String): ApiResult<T> {
        Timber.e(message)
        return ApiResult.error("Network call has failed for a following reason: $message")
    }

    private fun <T> errorMsg(e: Exception): ApiResult<T> {
        var message: String = e.message!!;
        if (e is ErrorHandler.NoInternetException) {
            message = e.message!!
        }
        Timber.e(message)
        return ApiResult.error(message)
    }

}

