package com.it2161.dit99999x.assignment1.ui.components


import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.it2161.dit99999x.assignment1.data.UserViewModel


@Composable
fun RegisterScreen(navController: NavController, userViewModel: UserViewModel) {
    var userId by remember { mutableStateOf(userViewModel.userId.value) }
    var preferredName by remember { mutableStateOf(userViewModel.preferredName.value) }
    var password by remember { mutableStateOf(userViewModel.password.value) }
    var email by remember { mutableStateOf(userViewModel.email.value) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Register", style = MaterialTheme.typography.h5)

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
            value = preferredName,
            onValueChange = {
                preferredName = it
                userViewModel.preferredName.value = it
            },
            label = { Text("Preferred Name") },
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

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                userViewModel.email.value = it
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Handle registration logic here
                // Save the user data in the ViewModel
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to navigate back to Login screen
        TextButton(
            onClick = {
                navController.popBackStack() // Navigate back to the previous screen (Login)
            }
        ) {
            Text("Already have an account? Login")
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        navController = rememberNavController(),
        userViewModel = UserViewModel()
    )

}