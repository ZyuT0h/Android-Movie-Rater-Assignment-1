package com.it2161.dit99999x.assignment1.data

data class MovieResponse(
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: Float
)

data class MovieDetails(
    val title: String,
    val adult: Boolean,
    val genres: List<String>,
    val originalLanguage: String,
    val releaseDate: String,
    val runtime: Int,
    val voteCount: Int,
    val voteAverage: Double,
    val overview: String,
    val revenue: Long
)