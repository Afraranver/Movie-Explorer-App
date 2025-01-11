package com.example.movieexplorerapp.common

object Constants {

    object Timeout {
        const val CONNECT       = 20L
        const val READ          = 60L
        const val WRITE         = 120L
    }

    object Network {
        const val PAGE_SIZE             = 10
        const val STARTING_PAGE_INDEX   = 1
    }

    object StatusMessages {
        const val UNAUTHORIZED                  = "Unauthorized!"
        const val FORBIDDEN                     = "Forbidden!"
        const val NOT_FOUND                     = "Not Found"
        const val REQUEST_TIMEOUT               = "Request Timed Out"
        const val INTERNAL_SERVER_ERROR         = "Internal Server Error"
        const val BAD_GATEWAY                   = "Bad Gateway!"
        const val SERVICE_UNAVAILABLE           = "Service Unavailable!"
        const val GATEWAY_TIMEOUT               = "Gateway Timeout"
        const val DEFAULT                       = "Something went wrong, Please try again!"
    }

    object Errors {
        const val NETWORK_FAILURE               = "You are not connected to the internet. Make sure your network connection and try again."
        const val CONVERSION_FAILURE            = "Conversion Error"
    }

    const val LANG = "en-US/"
}