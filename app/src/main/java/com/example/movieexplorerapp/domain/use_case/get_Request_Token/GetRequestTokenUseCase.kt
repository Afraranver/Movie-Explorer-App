package com.example.movieexplorerapp.domain.use_case.get_Request_Token

import com.example.movieexplorerapp.common.Resource
import com.example.movieexplorerapp.data.remote.dto.RequestTokenResponse
import com.example.movieexplorerapp.domain.respository.TMDbRepository
import java.io.IOException
import javax.inject.Inject

class GetRequestTokenUseCase @Inject constructor(
    private val repository: TMDbRepository
) {
    suspend operator fun invoke(apiKey: String): Resource<RequestTokenResponse> {
        return try {
            val requestTokenResponse = repository.getRequestToken(apiKey)
            Resource.Success(requestTokenResponse)
        } catch (e: retrofit2.HttpException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred.")
        } catch (e: IOException) {
            Resource.Error(e.localizedMessage ?: "Couldn't reach the server, check your internet!")
        }
    }
}