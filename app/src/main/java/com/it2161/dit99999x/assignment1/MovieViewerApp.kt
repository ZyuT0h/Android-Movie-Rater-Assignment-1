package com.it2161.dit99999x.assignment1

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.it2161.dit99999x.assignment1.data.UserViewModel
import com.it2161.dit99999x.assignment1.ui.components.LandingScreen
import com.it2161.dit99999x.assignment1.ui.components.LoginScreen
import com.it2161.dit99999x.assignment1.ui.components.RegisterScreen

@Composable
fun MovieViewerApp() {
    // Initialize Navigation Controller
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val modifier = Modifier.fillMaxSize().padding(innerPadding)

        Log.d("App data : ", "" + MovieRaterApplication.instance.data.size)
        if (MovieRaterApplication.instance.userProfile != null) {
            Log.d("User profile : ", "" + MovieRaterApplication.instance.userProfile!!.userId)
        } else {
            Log.d("User profile : ", "No user profile saved")
        }

        // Add Navigation Host
        NavigationHost(
            navController = navController,
            modifier = modifier
        )
    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = "login", // Define your start screen
        modifier = modifier
    ) {
        // Define navigation routes (composable screens)
        composable("login") { LoginScreen(
            navController,
            userViewModel = UserViewModel()
        ) }
        composable("register") { RegisterScreen(
            navController,
            userViewModel = UserViewModel()
        ) }
        composable("landing") { LandingScreen(navController) } // Example: Add a Home screen
    }
}




