package com.it2161.dit99999x.assignment1


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.it2161.dit99999x.assignment1.data.OfflineUserRepository
import com.it2161.dit99999x.assignment1.data.User
import com.it2161.dit99999x.assignment1.data.UserDatabase
import com.it2161.dit99999x.assignment1.data.UserViewModel
import com.it2161.dit99999x.assignment1.data.UserViewModelFactory

import com.it2161.dit99999x.assignment1.ui.theme.Assignment1Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment1Theme {
                MovieViewerApp()
            }
        }

        val userDAO = UserDatabase.getDatabase(applicationContext).userDAO()
        val userRepository = OfflineUserRepository(userDAO)
        val factory = UserViewModelFactory(userDAO, userRepository)
        val viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        // Test database operation (Should ideally be in a ViewModel or Repository)
        CoroutineScope(Dispatchers.IO).launch {
            val testUser = User(userName = "Zac", preferredName = "Zyu", password = "050303")
            val existingUser = userDAO.getUserByName(testUser.userName)

            if (existingUser == null) {
                userDAO.insertUser(testUser)
                println("Zac inserted")
            } else {
                println("Zac already exists")
            }

            val users = userDAO.getAllUsers()
            println(users)  // Log users to verify database access
        }

    }
}





