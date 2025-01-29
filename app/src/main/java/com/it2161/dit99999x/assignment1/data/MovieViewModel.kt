package com.it2161.dit99999x.assignment1.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val repository = MovieRepository(RetrofitInstance.api)
    private val apiKey = "171db22672f061cb691f3a869f17b082"

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

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> = _movieDetails

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                val movie = repository.getMovieDetails(movieId, apiKey)
                _movieDetails.value = movie
                Log.d("MovieDetails", "Fetched: $movie")
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


}