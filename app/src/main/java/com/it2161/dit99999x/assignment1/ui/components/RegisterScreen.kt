package com.it2161.dit99999x.assignment1.ui.components


import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.it2161.dit99999x.assignment1.data.User
import com.it2161.dit99999x.assignment1.data.UserDAO
import com.it2161.dit99999x.assignment1.data.UserViewModel


@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
}

@Composable
fun RegisterUserScreen(navController: NavController, userViewModel: UserViewModel) {
    val userUiState by userViewModel.userUiState.collectAsState()
    val context = LocalContext.current

    // Center the form using Box
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center // Centers content on the screen
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.85f), // Adjust width to 85% of the screen
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // User ID Field
            OutlinedTextField(
                value = userUiState.userName,
                onValueChange = { userViewModel.updateUsername(it) },
                label = { Text("User ID") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Preferred Name Field
            OutlinedTextField(
                value = userUiState.preferredName,
                onValueChange = { userViewModel.updatePreferredName(it) },
                label = { Text("Preferred Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password Field
            OutlinedTextField(
                value = userUiState.password,
                onValueChange = { userViewModel.updatePassword(it) },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Register Button
            // Register Button
            Button(
                onClick = {
                    // Check if any field is empty
                    if (userUiState.userName.isBlank() ||
                        userUiState.preferredName.isBlank() ||
                        userUiState.password.isBlank()) {

                        Toast.makeText(context, "All fields must be filled!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    userViewModel.registerUser(
                        onSuccess = {
                            Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                            navController.navigate("landing")
                        },
                        onError = { errorMessage ->
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = userUiState.userName.isNotBlank() &&
                        userUiState.preferredName.isNotBlank() &&
                        userUiState.password.isNotBlank() // Disable button if any field is empty
            ) {
                Text("Register")
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Login Navigation
            TextButton(
                onClick = { navController.popBackStack() }
            ) {
                Text("Already have an account? Login")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegisterUserScreenPreview() {
    val context = LocalContext.current

    // Set up a persistent database (not in-memory)
    val database = Room.databaseBuilder(
        context,
        AppDatabase::class.java, // Your database class
        "user_database" // Provide a name for the database file
    ).fallbackToDestructiveMigration() // This will handle migrations for you (optional)
        .build()

    // Get the UserDAO from the persistent database
    val userDao = database.userDao()

    // Create the ViewModel with the UserDAO
    RegisterUserScreen(
        navController = rememberNavController(),
        userViewModel = UserViewModel(
            userDao,
            userRepository = TODO()
        ) // Pass the userDao to the ViewModel
    )
}