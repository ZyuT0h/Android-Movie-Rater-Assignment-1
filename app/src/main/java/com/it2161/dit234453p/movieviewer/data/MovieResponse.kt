package com.it2161.dit234453p.movieviewer.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

data class MovieResponse(
    val results: List<Movie>
)
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: Float
)

@Entity(tableName = "movie_details")
data class MovieDetails(
    @PrimaryKey val id: Int,
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

@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey val id: Int,
    val name: String
)

data class GenreResponse(
    val genres: List<Genre>
)

data class ReviewResponse(
    val results: List<Review>
)

data class Review(
    val author: String,
    val content: String,
    val created_at: String
)

@Entity(
    tableName = "movie_genre_cross_ref",
    primaryKeys = ["movieId", "genreId"],
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Genre::class,
            parentColumns = ["id"],
            childColumns = ["genreId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("movieId"), Index("genreId")]
)
data class MovieGenreCrossRef(
    val movieId: Int,
    val genreId: Int
)