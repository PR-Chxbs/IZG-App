package com.prince.izg.ui.endpoints.admin.viewmodel.post

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prince.izg.data.remote.dto.Post.PostRequest
import com.prince.izg.data.remote.dto.Post.PostResponse
import com.prince.izg.data.remote.dto.User.UserResponse
import com.prince.izg.data.repository.PostRepository
import com.prince.izg.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PostUiState(
    val posts: List<PostResponse> = emptyList(),
    val selectedPost: PostResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

class PostViewModel(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostUiState())
    val uiState: StateFlow<PostUiState> = _uiState

    private val _isPostCreated = MutableStateFlow(false)
    val isPostCreated: StateFlow<Boolean> = _isPostCreated

    fun getPosts(token: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = postRepository.getPosts()
                if (response.isSuccessful) {
                    val posts = response.body() ?: emptyList()
                    _uiState.update { it.copy(posts = posts, isLoading = false) }
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun getPostById(token: String, id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = postRepository.getPostById(token, id)
                if (response.isSuccessful) {
                    _uiState.update { it.copy(selectedPost = response.body(), isLoading = false) }
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun addPost(token: String, post: PostRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = postRepository.addPost(token, post)
                if (response.isSuccessful) {
                    getPosts(token)
                    _isPostCreated.value = true
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                    _isPostCreated.value = false
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
                _isPostCreated.value = false
            }
        }
    }

    fun updatePost(token: String, id: Int, post: PostRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = postRepository.updatePost(token, id, post)
                if (response.isSuccessful) {
                    getPosts(token)
                    _isPostCreated.value = true
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                    _isPostCreated.value = false
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
                _isPostCreated.value = false
            }
        }
    }

    fun deletePost(token: String, id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = postRepository.deletePost(token, id)
                if (response.isSuccessful) {
                    getPosts(token)
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun publishPost(token: String, id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = postRepository.publishPost(token, id)
                if (!response.isSuccessful) {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun clearSelectedPost() {
        _uiState.update { it.copy(selectedPost = null) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun resetPostCreatedFlag() {
        _isPostCreated.value = false
    }
}
