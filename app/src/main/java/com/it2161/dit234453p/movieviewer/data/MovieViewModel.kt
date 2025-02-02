package com.it2161.dit234453p.movieviewer.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieViewModel(private val syncManager: MovieSyncManager,
                     private val repository: MovieRepository,
                     private val movieDao: MovieDao
    ) : ViewModel() {
    private val apiKey = "171db22672f061cb691f3a869f17b082"

    // Sync movies using the syncManager
    fun syncMovies() {
        viewModelScope.launch {
            try {
                syncManager.syncMovies(apiKey)
                Log.d("MovieViewModel", "Movies synced successfully")
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error syncing movies: ${e.message}")
            }
        }
    }

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    fun fetchMovies(endpoint: String) {
        viewModelScope.launch {
            val response = when (endpoint) {
                "popular" -> repository.getPopularMovies(apiKey)
                "now_playing" -> repository.getNowPlayingMovies(apiKey)
                "top_rated" -> repository.getTopRatedMovies(apiKey)
                "upcoming" -> repository.getUpcomingMovies(apiKey)
                else -> throw IllegalArgumentException("Invalid endpoint")
            }
            _movies.postValue(response.results)
        }
    }

    private val _movieDetails = MutableLiveData<MovieDetails?>()
    val movieDetails: LiveData<MovieDetails?> get() = _movieDetails

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                // Sync movie details with the database
                syncManager.syncMovieDetails(movieId, apiKey)

                // Fetch movie details from the local database
                val details = movieDao.getMovieDetails(movieId)
                _movieDetails.value = details // Assign directly, even if null
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching movie details: ${e.message}")
            }
        }
    }

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> = _reviews

    fun fetchMovieReviews(movieId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getMovieReviews(movieId, apiKey)
                _reviews.value = response.results
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching reviews: ${e.message}")
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.searchMovies(query, apiKey)  // Call API directly
                _movies.value = response.results  // Update movies LiveData
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching reviews: ${e.message}")
            }
        }
    }

    private val _similarMovies = MutableLiveData<List<Movie>>()
    val similarMovies: LiveData<List<Movie>> get() = _similarMovies

    fun fetchSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            try {
                _similarMovies.value = repository.fetchSimilarMovies(movieId, apiKey)
            } catch (e: Exception) {
                _similarMovies.value = emptyList()
            }
        }
    }

    fun syncAllData() {
        viewModelScope.launch {
            try {
                // Sync genres
                syncManager.syncGenres(apiKey)

                // Sync movies
                syncManager.syncMovies(apiKey)

                // Sync movie details and genres for all movies
                val movies = movieDao.getAllMovies()
                for (movie in movies) {
                    syncManager.syncMovieDetails(movie.id, apiKey)
                    val details = movieDao.getMovieDetails(movie.id)
                    details?.genres?.let { genres ->
                        syncManager.syncMovieGenres(movie.id, genres)
                    }
                }

                Log.d("MovieViewModel", "All data synced successfully")
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error syncing all data: ${e.message}")
            }
        }
    }
}