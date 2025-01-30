package com.it2161.dit99999x.assignment1.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserProfile(
    val id: Int = 0,
    val userName: String = "",
    val preferredName: String = "",
    val password: String = ""
)

class UserViewModel(
    private val userDAO: UserDAO, // Add this as a dependency
    private val userRepository: UserRepository // Add this as a dependency
) : ViewModel() {

    private val _user_ui_state = MutableStateFlow(UserProfile())
    val userUiState: StateFlow<UserProfile> = _user_ui_state.asStateFlow()

    init {
        reset()
    }

    fun reset() {
        _user_ui_state.value = UserProfile()
    }

    fun updateUsername(newUserName: String) {
        _user_ui_state.update { currentState -> currentState.copy(userName = newUserName) }
    }

    fun updatePreferredName(newPreferredName: String) {
        _user_ui_state.update { currentState -> currentState.copy(preferredName = newPreferredName) }
    }

    fun updatePassword(newPassword: String) {
        _user_ui_state.update { currentState -> currentState.copy(password = newPassword) }
    }

    // Register the user in the database
    fun registerUser(onSuccess: () -> Unit, onError: (String) -> Unit) {
        // Convert UI state to User entity
        val user = User(
            userName = _user_ui_state.value.userName,
            preferredName = _user_ui_state.value.preferredName,
            password = _user_ui_state.value.password
        )

        viewModelScope.launch {
            try {
                // Save the user data to the database
                userDAO.insertUser(user)
                onSuccess()
            } catch (e: Exception) {
                onError("Failed to save user data")
            }
        }
    }

    fun loadUserById(userId: Int) {
        viewModelScope.launch {
            userRepository.getUserStream(userId).collect { user ->
                user?.let {
                    _user_ui_state.update { currentState ->
                        currentState.copy(
                            id = it.id,
                            userName = it.userName,
                            preferredName = it.preferredName,
                            password = it.password
                        )
                    }
                }
            }
        }
    }

    private val _loggedInUser = mutableStateOf<User?>(null)
    val loggedInUser: State<User?> = _loggedInUser

    fun setLoggedInUser(user: User) {
        _loggedInUser.value = user
    }

}

class UserViewModelFactory(
    private val userDAO: UserDAO, // Add UserDAO here
    private val userRepository: UserRepository // Pass UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Create and return the UserViewModel with both dependencies
        return UserViewModel(userDAO, userRepository) as T
    }
}
