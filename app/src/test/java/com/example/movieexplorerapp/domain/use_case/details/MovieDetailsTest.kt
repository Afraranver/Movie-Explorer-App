package com.example.movieexplorerapp.domain.use_case.details

import com.example.movieexplorerapp.common.Constants
import com.example.movieexplorerapp.common.NetworkResult
import com.example.movieexplorerapp.domain.model.details.MovieDetailsResponse
import com.example.movieexplorerapp.domain.respository.TMDbRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class MovieDetailsTest {

    private lateinit var movieDetails: MovieDetails
    private lateinit var tmDbRepository: TMDbRepository

    @BeforeEach
    fun setUp() {
        tmDbRepository = mock()
        movieDetails = MovieDetails(tmDbRepository)
    }

    @Test
    fun `test invoke returns successful result`() = runTest {
        // Arrange
        val movieDetailsResponse = MovieDetailsResponse()
        val response: Response<MovieDetailsResponse> = mock()
        whenever(response.body()).thenReturn(movieDetailsResponse)
        val expectedResponse = NetworkResult.Success(response)

        // Mock the repository to return the response in the flow
        whenever(tmDbRepository.movieDetails("en", "1234")).thenReturn(flowOf(expectedResponse))

        // Act
        val result = movieDetails("en", "1234").first()

        // Assert
        Assertions.assertTrue(result is NetworkResult.Success)
        Assertions.assertEquals(expectedResponse, result)
    }

    @Test
    fun `test invoke returns error result`() = runTest {
        // Arrange
        val expectedError = NetworkResult.Failure(false, null, null, Constants.Errors.CONVERSION_FAILURE)

        Mockito.`when`(tmDbRepository.movieDetails(any(), any()))
            .thenReturn(flowOf(expectedError))

        // Act
        val result = movieDetails("en", "1234").first()

        // Assert
        Assertions.assertTrue(result is NetworkResult.Failure)
        Assertions.assertEquals(expectedError, result)
    }
}