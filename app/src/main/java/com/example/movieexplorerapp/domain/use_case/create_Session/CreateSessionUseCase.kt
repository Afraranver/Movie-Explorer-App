package com.example.movieexplorerapp.domain.use_case.create_Session

import com.example.movieexplorerapp.common.Resource
import com.example.movieexplorerapp.data.remote.dto.SessionResponse
import com.example.movieexplorerapp.domain.respository.TMDbRepository
import java.io.IOException
import javax.inject.Inject

class CreateSessionUseCase @Inject constructor(
    private val repository: TMDbRepository
) {
    suspend operator fun invoke(apiKey: String, requestToken: String): Resource<SessionResponse> {
        return try {
            val sessionResponse = repository.getSessionId(apiKey, requestToken)
            Resource.Success(sessionResponse)
        } catch (e: retrofit2.HttpException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred.")
        } catch (e: IOException) {
            Resource.Error(e.localizedMessage ?: "Couldn't reach the server, check your internet!")
        }
    }
}
