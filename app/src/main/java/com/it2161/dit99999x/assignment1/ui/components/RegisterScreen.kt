package com.it2161.dit99999x.assignment1.ui.components


import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val userUiState by userViewModel.userUiState.collectAsState()
        val context = LocalContext.current

        androidx.compose.material3.Text("Register", style = MaterialTheme.typography.labelMedium)

        androidx.compose.material3.OutlinedTextField(
            value = userUiState.userName,
            onValueChange = { userViewModel.updateUsername(it) },
            label = { androidx.compose.material3.Text("User ID") },
            modifier = Modifier.fillMaxWidth()
        )

        androidx.compose.material3.OutlinedTextField(
            value = userUiState.preferredName,
            onValueChange = { userViewModel.updatePreferredName(it) },
            label = { androidx.compose.material3.Text("Preferred Name") },
            modifier = Modifier.fillMaxWidth()
        )

        androidx.compose.material3.OutlinedTextField(
            value = userUiState.password,
            onValueChange = { userViewModel.updatePassword(it) },
            label = { androidx.compose.material3.Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )


        Spacer(modifier = Modifier.height(16.dp))

        androidx.compose.material3.Button(
            onClick = {
                userViewModel.registerUser(
                    onSuccess = {
                        Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT)
                            .show()
                        navController.navigate("landing")
                    },
                    onError = { errorMessage ->
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material3.Text("Register")
        }


        // Button to navigate back to Login screen
        androidx.compose.material3.TextButton(
            onClick = {
                navController.popBackStack() // Navigate back to the previous screen (Login)
            }
        ) {
            androidx.compose.material3.Text("Already have an account? Login")
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