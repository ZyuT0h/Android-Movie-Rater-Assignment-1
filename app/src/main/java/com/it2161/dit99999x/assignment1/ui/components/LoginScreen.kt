package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.it2161.dit99999x.assignment1.data.UserRepository
import com.it2161.dit99999x.assignment1.data.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController, userRepository: UserRepository, userViewModel: UserViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Login", style = MaterialTheme.typography.labelMedium)
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("User ID") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth(), visualTransformation = PasswordVisualTransformation())

        Button(
            onClick = {
                errorMessage = ""
                successMessage = ""
                if (username.isNotBlank() && password.isNotBlank()) {
                    scope.launch {
                        try {
                            val user = userRepository.getUserByUsername(username)
                            if (user != null && user.password == password) {
                                successMessage = "Login successful! Welcome, ${user.preferredName}"

                                // Store user data in ViewModel
                                userViewModel.setLoggedInUser(user)

                                // Navigate to landing page
                                navController.navigate("landing")
                            } else {
                                errorMessage = "Invalid User ID or Password"
                            }
                        } catch (e: Exception) {
                            errorMessage = "User not found or an error occurred"
                        }
                    }
                } else {
                    errorMessage = "Please fill in all fields"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        // Show success or error messages
        if (errorMessage.isNotBlank()) Text(errorMessage, color = MaterialTheme.colorScheme.error)
        if (successMessage.isNotBlank()) Text(successMessage, color = MaterialTheme.colorScheme.primary)

        TextButton(onClick = { navController.navigate("register") }) {
            Text("Don't have an account? Register")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        navController = rememberNavController(),
        userRepository = TODO(),
        userViewModel = TODO()
    )
}


