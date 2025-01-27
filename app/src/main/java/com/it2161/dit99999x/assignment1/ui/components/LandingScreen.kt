package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.it2161.dit99999x.assignment1.MovieRaterApplication

@Composable
fun LandingScreen(navController: NavController) {


    val context = LocalContext.current
    var movieList = MovieRaterApplication().data
    Text("Hello Landing Screen")

}

@Preview
@Composable
fun LandingScreenPreview() {

    LandingScreen(navController = rememberNavController())
}




