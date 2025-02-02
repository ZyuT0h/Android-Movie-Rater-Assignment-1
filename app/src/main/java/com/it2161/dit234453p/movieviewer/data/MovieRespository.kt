package com.it2161.dit234453p.movieviewer.data

class MovieRepository(private val api: TMDBApi) {
    suspend fun getPopularMovies(apiKey: String) = api.getPopularMovies(apiKey)
    suspend fun getNowPlayingMovies(apiKey: String) = api.getNowPlayingMovies(apiKey)
    suspend fun getTopRatedMovies(apiKey: String) = api.getTopRatedMovies(apiKey)
    suspend fun getUpcomingMovies(apiKey: String) = api.getUpcomingMovies(apiKey)
    suspend fun getMovieDetails(movieId: Int, apiKey: String) = api.getMovieDetails(movieId, apiKey)
    suspend fun getMovieReviews(movieId: Int, apiKey: String) = api.getMovieReviews(movieId, apiKey)
    suspend fun searchMovies(query: String, apiKey: String) = api.searchMovies(apiKey, query)
    suspend fun fetchSimilarMovies(movieId: Int, apiKey: String): List<Movie> {
        return try {
            api.getSimilarMovies(movieId, apiKey).results  // Extract the 'results' list here
        } catch (e: Exception) {
            emptyList()  // Return an empty list in case of error
        }
    }
    suspend fun getGenres(apiKey: String): List<Genre> {
        val response = api.getGenres(apiKey)
        return response.genres
    }
}

