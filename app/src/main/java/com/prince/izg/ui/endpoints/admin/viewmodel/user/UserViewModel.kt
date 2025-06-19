package com.prince.izg.ui.endpoints.admin.viewmodel.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.izg.data.remote.dto.User.UserRequest
import com.prince.izg.data.remote.dto.User.UserResponse
import com.prince.izg.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserUiState(
    val users: List<UserResponse> = emptyList(),
    val selectedUser: UserResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState

    private val _isUserCreated = MutableStateFlow(false)
    val isUserCreated: StateFlow<Boolean> = _isUserCreated

    fun getUsers(token: String) {
        Log.d("UsersScreen", "(UserViewModel) Triggered getUsers")
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = userRepository.getUsers(token)
                Log.d("UsersScreen", "(UserViewModel) Response successful: ${response.isSuccessful}")
                if (response.isSuccessful) {
                    val users = response.body() ?: emptyList()
                    Log.d("UsersScreen", "(UserViewModel) Fetched users: $users")
                    _uiState.update { it.copy(users = users, isLoading = false) }
                } else {
                    _uiState.update {
                        it.copy(error = response.message(), isLoading = false)
                    }

                }
            } catch (e: Exception) {
                Log.d("UsersScreen", "(From ViewModel) Exception: ${e.message}")
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun getUserById(token: String, id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = userRepository.getUserById(token, id)
                if (response.isSuccessful) {
                    _uiState.update { it.copy(selectedUser = response.body(), isLoading = false) }
                } else {
                    _uiState.update {
                        it.copy(error = response.message(), isLoading = false)
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun addUser(token: String, user: UserRequest) {
        Log.d("AddOrEditUserScreen", "(UserViewModel) Add user triggered")
        viewModelScope.launch {
            Log.d("AddOrEditUserScreen", "(UserViewModel) Entered view model scope")
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = userRepository.addUser(token, user)
                if (response.isSuccessful) {
                    Log.d("AddOrEditUserScreen", "(UserViewModel) Add user successful")
                    getUsers(token) // refresh the list
                    _isUserCreated.value = true
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.d("AddOrEditUserScreen", "(UserViewModel) Error: $errorBody")

                    _uiState.update {
                        it.copy(
                            error = errorBody ?: "Unknown error",
                            isLoading = false
                        )
                    }
                    _isUserCreated.value = false
                }
            } catch (e: Exception) {
                Log.d("AddOrEditUserScreen", "Error: ${e.message}")
                _uiState.update { it.copy(error = e.message, isLoading = false) }
                _isUserCreated.value = false
            }
        }
    }

    fun updateUser(token: String, id: Int, user: UserRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = userRepository.updateUserById(token, id, user)
                if (response.isSuccessful) {
                    getUsers(token)
                } else {
                    _uiState.update {
                        it.copy(error = response.message(), isLoading = false)
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun deleteUser(token: String, id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = userRepository.deleteUserById(token, id)
                if (response.isSuccessful) {
                    getUsers(token)
                } else {
                    _uiState.update {
                        it.copy(error = response.message(), isLoading = false)
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun clearSelectedUser() {
        _uiState.update { it.copy(selectedUser = null) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
    fun resetUserCreatedFlag() {
        _isUserCreated.value = false
    }
}
