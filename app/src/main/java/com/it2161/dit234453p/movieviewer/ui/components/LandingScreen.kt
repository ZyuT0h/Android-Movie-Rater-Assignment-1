package com.it2161.dit234453p.movieviewer.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.it2161.dit234453p.movieviewer.data.MovieViewModel

@Composable
fun LandingScreen(
    viewModel: MovieViewModel = viewModel(),
    navController: NavController
) {
    // Observe movies from ViewModel
    val movies by viewModel.movies.observeAsState(emptyList())
    var expanded by remember { mutableStateOf(false) } // Dropdown state
    var selectedCategory by remember { mutableStateOf("Popular") } // Current category
    val searchQuery = remember { mutableStateOf("") }

    // Map categories to API endpoints
    val categoryEndpoints = mapOf(
        "Popular" to "popular",
        "Now Playing" to "now_playing",
        "Top Rated" to "top_rated",
        "Upcoming" to "upcoming"
    )

    // Fetch movies based on search query or selected category
    LaunchedEffect(searchQuery.value) {
        if (searchQuery.value.isNotEmpty()) {
            viewModel.searchMovies(searchQuery.value) // Trigger search with the query
        } else {
            val endpoint = categoryEndpoints[selectedCategory] ?: "popular"
            viewModel.fetchMovies(endpoint) // Default fetch based on selected category
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Search Bar and Category Dropdown
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Search Field
                TextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    placeholder = { Text("Search Movies...") },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        // Trigger search when the user presses "Search" on the keyboard
                        viewModel.searchMovies(searchQuery.value)
                    }),
                    modifier = Modifier.weight(1f)
                )

                // Category Dropdown
                Box(modifier = Modifier.padding(start = 8.dp)) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        TextButton(
                            onClick = { expanded = true },
                            modifier = Modifier.wrapContentWidth()
                        ) {
                            Text(
                                text = selectedCategory,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categoryEndpoints.keys.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(text = category) },
                                onClick = {
                                    expanded = false
                                    selectedCategory = category
                                    val endpoint = categoryEndpoints[category] ?: "popular"
                                    viewModel.fetchMovies(endpoint)
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display Movies in a Grid
            if (movies.isEmpty()) {
                // Show a message if no movies are found
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchQuery.value.isNotEmpty()) "No results found." else "Loading...",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    itemsIndexed(movies) { _, movie ->
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    // Navigate to the Movie Details Screen with movie ID
                                    navController.navigate("details/${movie.id}")
                                },
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(8.dp)
                            ) {
                                // Movie Poster
                                Image(
                                    painter = rememberImagePainter(
                                        data = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                                        builder = {
                                            crossfade(true) // Smooth image loading
                                        }
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(0.7f)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Movie Title
                                Text(
                                    text = movie.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                // Movie Overview
                                Text(
                                    text = movie.overview,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                // Movie Rating
                                Text(
                                    text = "⭐ ${movie.vote_average}",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
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




