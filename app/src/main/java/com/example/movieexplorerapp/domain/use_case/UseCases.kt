package com.example.movieexplorerapp.domain.use_case

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

data class UseCases(
    //paging
    val popularMoviesPagingList: PopularMoviesPagingList,
    val nowPlayingMoviesPagingList: NowPlayingMoviesPagingList,
    val upcomingMoviesPagingList: UpcomingMoviesPagingList,
    val topRatedMoviesPagingList: TopRatedMoviesPagingList,
    //non-paging
    val popularMoviesList: PopularMoviesList,
    val nowPlayingMoviesList: NowPlayingMoviesList,
    val upcomingMoviesList: UpcomingMoviesList,
    val topRatedMoviesList: TopRatedMoviesList,
    val movieDetails: MovieDetails,
    val movieCredits: MovieCredits,
    val getVideos: GetVideos,
    val searchMoviesPagingList: SearchMoviesPagingList,
)