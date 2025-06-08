package com.prince.izg.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.io.IOException
import retrofit2.HttpException

// Project packages

import com.prince.izg.data.local.datastore.DataStoreManager
import com.prince.izg.data.remote.dto.Auth.LoginRequest
import com.prince.izg.data.remote.dto.Auth.RegisterRequest
import com.prince.izg.data.repository.AuthRepository

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _authToken = MutableStateFlow<String?>(null)
    val authToken: StateFlow<String?> = _authToken

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError

    private val _authSuccess = MutableStateFlow(false)
    val authSuccess: StateFlow<Boolean> = _authSuccess

    init {
        loadToken()
    }

    private fun loadToken() {
        viewModelScope.launch {
            dataStoreManager.getAuthToken().collect { token ->
                _authToken.value = token
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authRepository.loginUser(LoginRequest(email, password))
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    if (!token.isNullOrEmpty()) {
                        dataStoreManager.saveAuthToken(token)
                        _authToken.value = token
                        _authSuccess.value = true
                        _authError.value = null
                    } else {
                        _authError.value = "Login successful, but no token received"
                        _authSuccess.value = false
                    }
                } else {
                    _authError.value = response.errorBody()?.string() ?: "Login failed"
                    _authSuccess.value = false
                }
            } catch (e: IOException) {
                _authError.value = "Network error: ${e.message}"
                _authSuccess.value = false
            } catch (e: HttpException) {
                _authError.value = "HTTP error: ${e.message}"
                _authSuccess.value = false
            } catch (e: Exception) {
                _authError.value = "Unexpected error: ${e.message}"
                _authSuccess.value = false
            }
        }
    }

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authRepository.registerUser(RegisterRequest(
                    username = username,
                    "",
                    "",
                    "",
                    "",
                    "",
                    email = email,
                    password = password))
                if (response.isSuccessful) {
                    _authSuccess.value = true
                    _authError.value = null
                } else {
                    _authError.value = response.errorBody()?.string() ?: "Registration failed"
                    _authSuccess.value = false
                }
            } catch (e: IOException) {
                _authError.value = "Network error: ${e.message}"
                _authSuccess.value = false
            } catch (e: HttpException) {
                _authError.value = "HTTP error: ${e.message}"
                _authSuccess.value = false
            } catch (e: Exception) {
                _authError.value = "Unexpected error: ${e.message}"
                _authSuccess.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.clearAuthToken()
            _authToken.value = null
            _authSuccess.value = false
        }
    }
}
