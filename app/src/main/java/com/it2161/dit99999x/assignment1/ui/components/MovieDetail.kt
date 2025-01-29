package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.it2161.dit99999x.assignment1.R
import com.it2161.dit99999x.assignment1.data.MovieDetails
import com.it2161.dit99999x.assignment1.data.MovieViewModel
import com.it2161.dit99999x.assignment1.data.Review

@Composable
fun MovieDetailScreen(navController: NavController, movieId: String, viewModel: MovieViewModel) {
    val movieDetails by viewModel.movieDetails.observeAsState()
    val reviews by viewModel.reviews.observeAsState(emptyList())

    LaunchedEffect(movieId) {
        if (movieId.isNotEmpty()) {
            viewModel.fetchMovieDetails(movieId.toInt())
            viewModel.fetchMovieReviews(movieId.toInt())
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        item {
            movieDetails?.let { movie ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = movie.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = if (movie.adult) "🔞 18+" else "✔️ PG",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (movie.adult) Color.Red else Color.Green
                        )
                    }

                    // Movie Poster Image
                    Image(
                        painter = rememberImagePainter(
                            "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                            builder = {
                                crossfade(true)
                                placeholder(R.drawable.placeholder)
                            }
                        ),
                        contentDescription = "Movie Poster",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Overview",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                        )

                        Text(
                            text = "⭐ ${movie.vote_average} / 10 (${movie.vote_count})",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD700),
                        )
                    }
                    Text(
                        text = movie.overview,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Column(modifier = Modifier.fillMaxWidth()) {
                        MovieDetailItem("Genres", movie.genres.joinToString { it.name })
                        movie.runtime?.let {
                            val runtimeHours = it / 60
                            val runtimeMinutes = it % 60
                            MovieDetailItem("Runtime", "${runtimeHours}h ${runtimeMinutes}m")
                        }

                        movie.release_date?.let {
                            MovieDetailItem("Release Date", it)
                        }

                        movie.original_language?.let {
                            MovieDetailItem("Original Language", it.uppercase())
                        }

                        MovieDetailItem("Revenue", "$${String.format("%,d", movie.revenue)}")
                    }


                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }
            } ?: Text("Loading movie details...")
        }

        // Reviews Section (add this section here, within the same LazyColumn)
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Reviews",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(reviews) { review ->
            ReviewItem(review)
        }
    }
}


@Composable
fun MovieDetailItem(label: String, value: String) {
    Text(
        text = "$label: $value",
        fontSize = 14.sp,
        color = Color.Gray,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}


@Composable
fun ReviewItem(review: Review) {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Review Author
            Text(
                text = review.author,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Review Content
            Text(
                text = review.content,
                fontSize = 14.sp,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Review Date
            Text(
                text = "Posted on: ${review.created_at}",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}
