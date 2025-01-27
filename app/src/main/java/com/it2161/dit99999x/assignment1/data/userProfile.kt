package com.it2161.dit99999x.assignment1.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// Define a User data model
data class UserProfile(
    val userId: String = "",
    val preferredName: String = "",
    val password: String = "",
    val email: String = ""
)

class UserViewModel : ViewModel() {

    // Store user data in mutable states
    var userId = mutableStateOf("")
    var preferredName = mutableStateOf("")
    var password = mutableStateOf("")
    var email = mutableStateOf("")

    // Add function to clear user data (e.g., for logout)
    fun clearUserData() {
        userId.value = ""
        preferredName.value = ""
        password.value = ""
        email.value = ""
    }

    // You can add logic for saving user data, registration, etc.
}