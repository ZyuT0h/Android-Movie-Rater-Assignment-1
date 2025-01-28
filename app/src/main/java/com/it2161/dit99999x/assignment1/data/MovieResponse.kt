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
    val id: Int,
    val title: String,
    val adult: Boolean,
    val genres: List<Genre>,
    val original_language: String?,
    val release_date: String?,
    val runtime: Int?,
    val vote_count: Int,
    val vote_average: Double,
    val overview: String,
    val revenue: Int?,
    val poster_path: String
)

data class Genre(
    val id: Int,
    val name: String
)

data class ReviewResponse(
    val results: List<Review>
)

data class Review(
    val author: String,
    val content: String,
    val created_at: String
)