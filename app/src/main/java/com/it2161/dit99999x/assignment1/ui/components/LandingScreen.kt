package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
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
    val searchQuery = remember { mutableStateOf("") }

    val categories = listOf("Popular", "Now Playing", "Top Rated", "Upcoming")

    LaunchedEffect(searchQuery.value) {
        if (searchQuery.value.isNotEmpty()) { // Access the value of MutableState
            viewModel.searchMovies(searchQuery.value)  // Trigger search with the query
        } else {
            viewModel.fetchMovies("popular")  // Default fetch if search query is empty
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                // Search field
                TextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    placeholder = { Text("Search Movies...") },
                    modifier = Modifier.weight(1f)  // Make it take available space
                )

                // Category dropdown
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
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Movie Grid
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                // Use itemsIndexed instead of items if you're working with a list of objects like List<Movie>
                itemsIndexed(movies.value) { index, movie ->  // 'index' is the position, 'movie' is the actual data
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
                            Image(
                                painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${movie.poster_path}"),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(0.7f)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = movie.overview,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(4.dp))
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

@Preview
@Composable
fun LandingScreenPreview() {
    val mockNavController = rememberNavController()
    LandingScreen(navController = mockNavController)
}




