package com.it2161.dit99999x.assignment1

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.it2161.dit99999x.assignment1.data.MovieViewModel
import com.it2161.dit99999x.assignment1.data.OfflineUserRepository
import com.it2161.dit99999x.assignment1.data.UserDatabase
import com.it2161.dit99999x.assignment1.data.UserViewModel
import com.it2161.dit99999x.assignment1.ui.components.LandingScreen
import com.it2161.dit99999x.assignment1.ui.components.LoginScreen
import com.it2161.dit99999x.assignment1.ui.components.MovieDetailScreen
import com.it2161.dit99999x.assignment1.ui.components.ProfileScreen
import com.it2161.dit99999x.assignment1.ui.components.RegisterUserScreen

@Composable
fun MovieViewerApp() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavigationHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier) {
    val userViewModel: UserViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login", // Define your start screen
        modifier = modifier
    ) {
        // Define navigation routes (composable screens)
        composable("login") {
            val userDAO = UserDatabase.getDatabase(LocalContext.current).userDAO()
            val userRepository = OfflineUserRepository(userDAO)

            LoginScreen(
                navController = navController,
                userRepository = userRepository
            )
        }

        composable("register") {
            RegisterUserScreen(
                userViewModel = userViewModel,
                navController = navController
            )
        }
        composable("landing") { LandingScreen(
            navController = navController
        ) }

        composable("details/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            if (movieId != null) {
                MovieDetailScreen(
                    navController = navController,
                    movieId = movieId,
                    viewModel = MovieViewModel()
                )
            } else {
                Log.e("Navigation", "Movie ID is null")
            }
        }

        composable("profile/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            ProfileScreen(userId)
        }

    }
}




