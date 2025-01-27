package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.it2161.dit99999x.assignment1.data.MovieViewModel

@Composable
fun LandingScreen(viewModel: MovieViewModel = viewModel(), navController: NavController) {
    val movies = viewModel.movies.observeAsState(emptyList())
    var expanded by remember { mutableStateOf(false) } // Dropdown state
    var selectedCategory by remember { mutableStateOf("Popular") } // Current category

    val categories = listOf("Popular", "Now Playing", "Top Rated", "Upcoming")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Dropdown to select category
        Box(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = selectedCategory, fontWeight = FontWeight.Bold)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        selectedCategory = category
                        when (category) {
                            "Popular" -> viewModel.fetchMovies("popular")
                            "Now Playing" -> viewModel.fetchMovies("now_playing")
                            "Top Rated" -> viewModel.fetchMovies("top_rated")
                            "Upcoming" -> viewModel.fetchMovies("upcoming")
                        }
                    }) {
                        Text(text = category)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Movie List
        LazyColumn {
            items(movies.value) { movie ->
                Row(modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        // Navigate to the Movie Details Screen with movie ID
                        navController.navigate("details/${movie.id}")
                    }
                ) {
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
}


@Preview
@Composable
fun LandingScreenPreview() {
    val mockNavController = rememberNavController()
    LandingScreen(navController = mockNavController)
}




