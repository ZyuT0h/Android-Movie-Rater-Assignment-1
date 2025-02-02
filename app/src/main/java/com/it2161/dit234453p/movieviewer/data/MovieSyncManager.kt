package com.it2161.dit234453p.movieviewer.data

import android.util.Log

class MovieSyncManager(
    private val repository: MovieRepository,
    private val dao: MovieDao
) {

    suspend fun syncMovies(apiKey: String) {
        try {
            // Define the endpoints to fetch
            val endpoints = listOf("popular", "now_playing", "top_rated", "upcoming")

            // Clear existing movies before inserting new ones
            dao.clearAllMovies()

            // Fetch and save movies for each endpoint
            val allMovies = mutableListOf<Movie>()
            for (endpoint in endpoints) {
                val response = when (endpoint) {
                    "popular" -> repository.getPopularMovies(apiKey)
                    "now_playing" -> repository.getNowPlayingMovies(apiKey)
                    "top_rated" -> repository.getTopRatedMovies(apiKey)
                    "upcoming" -> repository.getUpcomingMovies(apiKey)
                    else -> throw IllegalArgumentException("Invalid endpoint")
                }
                allMovies.addAll(response.results)
            }

            // Map API response to local MovieEntity objects
            val movieEntities = allMovies.map { movie ->
                Movie(
                    id = movie.id,
                    title = movie.title,
                    overview = movie.overview,
                    poster_path = movie.poster_path,
                    release_date = movie.release_date,
                    vote_average = movie.vote_average
                )
            }

            // Insert all movies into the database
            dao.insertMovies(movieEntities)

            Log.d("MovieSyncManager", "Movies synced successfully")
        } catch (e: Exception) {
            Log.e("MovieSyncManager", "Error syncing movies: ${e.message}")
        }
    }

    suspend fun syncMovieDetails(movieId: Int, apiKey: String) {
        try {
            // Fetch detailed movie information from the API
            val movieDetails = repository.getMovieDetails(movieId, apiKey)

            // Map API response to local MovieDetailsEntity object
            val movieDetailsEntity = MovieDetails(
                id = movieDetails.id,
                title = movieDetails.title,
                adult = movieDetails.adult,
                original_language = movieDetails.original_language,
                release_date = movieDetails.release_date,
                runtime = movieDetails.runtime,
                vote_count = movieDetails.vote_count,
                vote_average = movieDetails.vote_average,
                overview = movieDetails.overview,
                revenue = movieDetails.revenue,
                poster_path = movieDetails.poster_path,
                genres = movieDetails.genres
            )

            // Insert or update the movie details in the database
            dao.insertMovieDetails(movieDetailsEntity)

            Log.d("MovieSyncManager", "Movie details synced successfully for ID: $movieId")
        } catch (e: Exception) {
            Log.e("MovieSyncManager", "Error syncing movie details: ${e.message}")
        }
    }

    suspend fun syncGenres(apiKey: String) {
        try {
            // Fetch genres from the API
            val genres = repository.getGenres(apiKey)

            // Map API response to local GenreEntity objects
            val genreEntities = genres.map { genre ->
                Genre(
                    id = genre.id,
                    name = genre.name
                )
            }

            // Clear existing genres and insert new ones
            dao.clearAllGenres()
            dao.insertGenres(genreEntities)

            Log.d("MovieSyncManager", "Genres synced successfully")
        } catch (e: Exception) {
            Log.e("MovieSyncManager", "Error syncing genres: ${e.message}")
        }
    }

    suspend fun syncMovieGenres(movieId: Int, genres: List<Genre>) {
        try {
            // Insert genres into the genre table
            val genreEntities = genres.map { genre ->
                Genre(
                    id = genre.id,
                    name = genre.name
                )
            }
            dao.insertGenres(genreEntities)

            // Insert cross references into the movie_genre_cross_ref table
            val crossRefs = genres.map { genre ->
                MovieGenreCrossRef(
                    movieId = movieId,
                    genreId = genre.id
                )
            }
            dao.insertMovieGenreCrossRefs(crossRefs)

            Log.d("MovieSyncManager", "Movie genres synced successfully for ID: $movieId")
        } catch (e: Exception) {
            Log.e("MovieSyncManager", "Error syncing movie genres: ${e.message}")
        }
    }
}