package com.example.movieexplorerapp.di

import com.example.movieexplorerapp.BuildConfig
import com.example.movieexplorerapp.common.Constants
import com.example.movieexplorerapp.data.remote.TMDbApiService
import com.example.movieexplorerapp.data.repository.FirebaseAuthRepository
import com.example.movieexplorerapp.domain.respository.AuthRepository
import com.example.movieexplorerapp.domain.respository.TMDbRepository
import com.example.movieexplorerapp.domain.use_case.UseCases
import com.example.movieexplorerapp.domain.use_case.details.GetVideos
import com.example.movieexplorerapp.domain.use_case.details.MovieCredits
import com.example.movieexplorerapp.domain.use_case.details.MovieDetails
import com.example.movieexplorerapp.domain.use_case.now_playing.NowPlayingMoviesList
import com.example.movieexplorerapp.domain.use_case.now_playing.NowPlayingMoviesPagingList
import com.example.movieexplorerapp.domain.use_case.popular.PopularMoviesList
import com.example.movieexplorerapp.domain.use_case.popular.PopularMoviesPagingList
import com.example.movieexplorerapp.domain.use_case.search_movie.SearchMoviesPagingList
import com.example.movieexplorerapp.domain.use_case.top_rated.TopRatedMoviesList
import com.example.movieexplorerapp.domain.use_case.top_rated.TopRatedMoviesPagingList
import com.example.movieexplorerapp.domain.use_case.upcoming.UpcomingMoviesList
import com.example.movieexplorerapp.domain.use_case.upcoming.UpcomingMoviesPagingList
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun providesGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun clientInterceptor(): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
            val newUrl = request.url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build()

            val newRequest = request.newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }


    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder().also { client ->
                    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                    }
                    client.addInterceptor(httpLoggingInterceptor)
                    client.connectTimeout(Constants.Timeout.CONNECT, TimeUnit.SECONDS)
                    client.readTimeout(Constants.Timeout.READ, TimeUnit.SECONDS)
                    client.writeTimeout(Constants.Timeout.WRITE, TimeUnit.SECONDS)
                    client.addNetworkInterceptor(clientInterceptor())
                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): TMDbApiService = retrofit.create(TMDbApiService::class.java)


    @Provides
    @Singleton
    fun useCases(tmDbRepository: TMDbRepository): UseCases = UseCases(

        //paging
        PopularMoviesPagingList(tmDbRepository),
        NowPlayingMoviesPagingList(tmDbRepository),
        UpcomingMoviesPagingList(tmDbRepository),
        TopRatedMoviesPagingList(tmDbRepository),

        //non-paging
        PopularMoviesList(tmDbRepository),
        NowPlayingMoviesList(tmDbRepository),
        UpcomingMoviesList(tmDbRepository),
        TopRatedMoviesList(tmDbRepository),

        MovieDetails(tmDbRepository),
        MovieCredits(tmDbRepository),
        GetVideos(tmDbRepository),
        SearchMoviesPagingList(tmDbRepository),
    )
}