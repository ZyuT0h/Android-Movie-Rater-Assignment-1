package com.it2161.dit234453p.movieviewer

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.it2161.dit234453p.movieviewer.data.MovieViewModel
import com.it2161.dit234453p.movieviewer.data.OfflineUserRepository
import com.it2161.dit234453p.movieviewer.data.UserDatabase
import com.it2161.dit234453p.movieviewer.data.UserViewModel
import com.it2161.dit234453p.movieviewer.ui.components.LandingScreen
import com.it2161.dit234453p.movieviewer.ui.components.LoginScreen
import com.it2161.dit234453p.movieviewer.ui.components.MovieDetailScreen
import com.it2161.dit234453p.movieviewer.ui.components.ProfileScreen
import com.it2161.dit234453p.movieviewer.ui.components.RegisterUserScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieViewerApp( movieViewModel: MovieViewModel,
                    userViewModel: UserViewModel) {
    val navController = rememberNavController()
    var showTopBar by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var currentRoute by remember { mutableStateOf("login") }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentRoute = destination.route ?: "login"
            showTopBar = currentRoute != "login" && currentRoute != "register"
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showTopBar) {
                TopAppBar(
                    title = { Text("Movie Viewer") },
                    navigationIcon = {
                        if (currentRoute.startsWith("details/")) { // Show back button only on MovieDetailScreen
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        }
                    },
                    actions = {
                        Box {
                            IconButton(onClick = { expanded = true }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "More Options")
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                if (currentRoute == "profile") {
                                    DropdownMenuItem(
                                        text = { Text("Movies") },
                                        onClick = {
                                            expanded = false
                                            navController.navigate("landing")
                                        }
                                    )
                                } else {
                                    DropdownMenuItem(
                                        text = { Text("Profile") },
                                        onClick = {
                                            expanded = false
                                            navController.navigate("profile")
                                        }
                                    )
                                }
                                DropdownMenuItem(
                                    text = { Text("Logout") },
                                    onClick = {
                                        expanded = false
                                        navController.navigate("login")
                                    }
                                )
                            }
                        }
                    }
                )
            }
        }

    ) { innerPadding ->
        NavigationHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            movieViewModel = movieViewModel, // Pass MovieViewModel
            userViewModel = userViewModel
        )
    }
}


@Composable
fun NavigationHost(
    navController: NavHostController,
    movieViewModel: MovieViewModel,
    userViewModel: UserViewModel,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "login", // Define your start screen
        modifier = modifier
    ) {
        composable("login") {
            val userDAO = UserDatabase.getDatabase(LocalContext.current).userDAO()
            val userRepository = OfflineUserRepository(userDAO)
            LoginScreen(
                navController = navController,
                userRepository = userRepository,
                userViewModel = userViewModel
            )
        }
        composable("register") {
            RegisterUserScreen(
                userViewModel = userViewModel,
                navController = navController
            )
        }
        composable("landing") {
            LandingScreen(
                navController = navController,
                viewModel = movieViewModel // Pass MovieViewModel to LandingScreen
            )
        }
        composable("details/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            if (movieId != null) {
                MovieDetailScreen(
                    navController = navController,
                    movieId = movieId,
                    viewModel = movieViewModel
                ) // Pass MovieViewModel to MovieDetailScreen
            } else {
                Log.e("Navigation", "Movie ID is null")
            }
        }

        composable("profile") {
            ProfileScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }
    }
}




