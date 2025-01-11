package com.example.movieexplorerapp.data.repository

import com.example.movieexplorerapp.data.remote.TMDbApiService
import com.example.movieexplorerapp.data.remote.dto.RequestTokenResponse
import com.example.movieexplorerapp.data.remote.dto.SessionResponse
import com.example.movieexplorerapp.domain.respository.TMDbRepository
import javax.inject.Inject

//class LoginRepositoryImpl @Inject constructor(
//    private val api: TMDbApiService
//) : TMDbRepository {
//    override suspend fun getRequestToken(apiKey: String): RequestTokenResponse {
//        return api.createRequestToken(apiKey)
//    }
//
//    override suspend fun getSessionId(apiKey: String, requestToken: String): SessionResponse {
//        return api.createSessionId(apiKey, mapOf("request_token" to requestToken))
//    }
//
//
//}