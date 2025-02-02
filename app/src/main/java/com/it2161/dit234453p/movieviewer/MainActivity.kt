package com.it2161.dit234453p.movieviewer


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.it2161.dit234453p.movieviewer.data.MovieViewModel
import com.it2161.dit234453p.movieviewer.data.OfflineUserRepository
import com.it2161.dit234453p.movieviewer.data.UserDatabase
import com.it2161.dit234453p.movieviewer.data.UserViewModel
import com.it2161.dit234453p.movieviewer.data.UserViewModelFactory
import com.it2161.dit234453p.movieviewer.ui.theme.Assignment1Theme



class MainActivity : ComponentActivity() {
    private lateinit var movieViewModel: MovieViewModel // Renamed to avoid conflict
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize the UserViewModel
        val userDAO = UserDatabase.getDatabase(applicationContext).userDAO()
        val userRepository = OfflineUserRepository(userDAO)
        val factory = UserViewModelFactory(userDAO, userRepository)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        // Get the application instance
        val app = application as MovieRaterApplication
        val movieDao = app.movieDatabase.movieDao()


        // Initialize the MovieViewModel with the syncManager and repository
        movieViewModel = MovieViewModel(
            app.syncManager, app.repository,
            movieDao
        )

        // Trigger movie synchronization
        movieViewModel.syncMovies()
        movieViewModel.syncAllData()

        // Set up Jetpack Compose content
        setContent {
            Assignment1Theme {
                MovieViewerApp(
                    movieViewModel = movieViewModel, // Pass MovieViewModel to Compose
                    userViewModel = userViewModel    // Pass UserViewModel to Compose
                )
            }
        }
    }
}





