package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.it2161.dit99999x.assignment1.data.MovieDetails

@Composable
fun MovieDetailScreen(navController: NavHostController, movieId: String) {
    // Simulate fetching details (replace with API call)
    val movieDetails = remember {
        MovieDetails(
            title = "Movie Title",
            adult = false,
            genres = listOf("Action", "Drama"),
            originalLanguage = "English",
            releaseDate = "2025-01-01",
            runtime = 120,
            voteCount = 1000,
            voteAverage = 8.5,
            overview = "This is a movie overview.",
            revenue = 1000000L
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Title: ${movieDetails.title}", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Genres: ${movieDetails.genres.joinToString()}")
        Text(text = "Language: ${movieDetails.originalLanguage}")
        Text(text = "Release Date: ${movieDetails.releaseDate}")
        Text(text = "Runtime: ${movieDetails.runtime} mins")
        Text(text = "Votes: ${movieDetails.voteCount}")
        Text(text = "Average Vote: ${movieDetails.voteAverage}")
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Overview: ${movieDetails.overview}")
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Revenue: $${movieDetails.revenue}")
    }
}
