package com.it2161.dit99999x.assignment1.data

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

}