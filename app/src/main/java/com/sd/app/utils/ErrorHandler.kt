package com.sd.app.utils

import java.io.IOException


object ErrorHandler {

    private const val NETWORK_ERROR_MESSAGE =
        "Please check your internet connectivity and try again!"
    private const val EMPTY_RESPONSE = "Server returned empty response."
    const val NO_SUCH_DATA = "Data not found in the database"
    const val UNKNOWN_ERROR = "An unknown error occurred!"
    const val REQUEST_TIME_OUT = "Request timed out. Please try again."


    class NoResponseException(message: String? = UNKNOWN_ERROR) : Exception(message)
    class SocketTimeOutException(message: String? = REQUEST_TIME_OUT) : Exception(message)

    class NoDataException(message: String? = NO_SUCH_DATA) : Exception(message)

    class NoInternetException(message: String? = NETWORK_ERROR_MESSAGE) : IOException(message)

}
