package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.it2161.dit99999x.assignment1.data.UserViewModel

@Composable
fun LoginScreen(navController: NavController, userViewModel: UserViewModel) {
    var userId by remember { mutableStateOf(userViewModel.userId.value) }
    var password by remember { mutableStateOf(userViewModel.password.value) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.h5)

        OutlinedTextField(
            value = userId,
            onValueChange = {
                userId = it
                userViewModel.userId.value = it
            },
            label = { Text("User ID") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                userViewModel.password.value = it
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Handle login logic here (authenticate user)
                // If login is successful, store the data in the ViewModel
                navController.navigate("landing") // Navigate to the Register screen

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to navigate to Register screen
        TextButton(
            onClick = {
                navController.navigate("register") // Navigate to the Register screen
            }
        ) {
            Text("Don't have an account? Register")
        }
    }
}

@Preview
@Composable
fun LoginUIPreview() {
    LoginScreen(
        navController = rememberNavController(),
        userViewModel = UserViewModel()
    )

}

