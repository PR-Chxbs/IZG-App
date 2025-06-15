package com.prince.izg.admin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.izg.data.remote.dto.User.UserRequest
import com.prince.izg.data.remote.dto.User.UserResponse
import com.prince.izg.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    fun getUsers(token: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = userRepository.getUsers(token)
                if (response.isSuccessful) {
                    val users = response.body() ?: emptyList()
                    _uiState.update { it.copy(users = users, isLoading = false) }
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
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = userRepository.addUser(token, user)
                if (response.isSuccessful) {
                    getUsers(token) // refresh the list
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
}
