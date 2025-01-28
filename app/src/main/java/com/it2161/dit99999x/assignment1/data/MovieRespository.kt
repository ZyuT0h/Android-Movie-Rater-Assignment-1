package com.it2161.dit99999x.assignment1.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val api: TMDBApi) {
    suspend fun getPopularMovies(apiKey: String) = api.getPopularMovies(apiKey)
    suspend fun getNowPlayingMovies(apiKey: String) = api.getNowPlayingMovies(apiKey)
    suspend fun getTopRatedMovies(apiKey: String) = api.getTopRatedMovies(apiKey)
    suspend fun getUpcomingMovies(apiKey: String) = api.getUpcomingMovies(apiKey)
    suspend fun getMovieDetails(movieId: Int, apiKey: String) = api.getMovieDetails(movieId, apiKey)
    suspend fun getMovieReviews(movieId: Int, apiKey: String) = api.getMovieReviews(movieId, apiKey)
}

