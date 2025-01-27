package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.it2161.dit99999x.assignment1.data.MovieViewModel

@Composable
fun LandingScreen(viewModel: MovieViewModel = viewModel()) {
    val movies = viewModel.movies.observeAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Movies",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            items(movies.value) { movie ->
                // MovieItem logic placed here
                Row(modifier = Modifier.padding(8.dp)) {
                    Image(
                        painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${movie.poster_path}"),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = movie.title, fontWeight = FontWeight.Bold)
                        Text(text = movie.overview, maxLines = 3)
                        Text(text = "Rating: ${movie.vote_average}")
                    }
                }
            }
        }
    }

    // Example: Fetch popular movies on screen load
    viewModel.fetchMovies("popular")
}


@Preview
@Composable
fun LandingScreenPreview() {

    LandingScreen()
}




