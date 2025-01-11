package com.example.movieexplorerapp.common
import org.json.JSONObject
import retrofit2.Response

object ResponseCodeManager {
    fun checkRetrofitApiResponse(response: Response<*>): String {
        when (response.code()) {
            401 -> {
                return Constants.StatusMessages.UNAUTHORIZED
            }
            403 -> {
                return Constants.StatusMessages.FORBIDDEN
            }
            404 -> {
                return Constants.StatusMessages.NOT_FOUND
            }
            408 -> {
                return Constants.StatusMessages.REQUEST_TIMEOUT
            }
            422 -> {
                return try {
                    val errorString = response.errorBody()?.byteStream()?.bufferedReader()
                        .use { it?.readText() } // defaults to UTF-8
                    val json = JSONObject(errorString)
                    json["message"].toString()
                } catch (e: Exception) {
                    "Invalid Credentials"
                }
            }
            500 -> {
                return Constants.StatusMessages.INTERNAL_SERVER_ERROR
            }
            502 -> {
                return Constants.StatusMessages.BAD_GATEWAY
            }
            503 -> {
                return Constants.StatusMessages.SERVICE_UNAVAILABLE
            }
            504 -> {
                return Constants.StatusMessages.GATEWAY_TIMEOUT
            }
            else -> {
                return Constants.StatusMessages.DEFAULT
            }
        }
    }
}