package com.prince.izg.admin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.izg.data.remote.dto.Category.CategoryRequest
import com.prince.izg.data.remote.dto.Category.CategoryResponse
import com.prince.izg.data.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CategoryUiState(
    val categories: List<CategoryResponse> = emptyList(),
    val selectedCategory: CategoryResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class CategoryViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState

    fun getCategories(token: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = categoryRepository.getCategories()
                if (response.isSuccessful) {
                    val categories = response.body() ?: emptyList()
                    _uiState.update { it.copy(categories = categories, isLoading = false) }
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun getCategoryById(token: String, id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = categoryRepository.getCategoryById(id)
                if (response.isSuccessful) {
                    _uiState.update { it.copy(selectedCategory = response.body(), isLoading = false) }
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun addCategory(token: String, category: CategoryRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = categoryRepository.addCategory(category)
                if (response.isSuccessful) {
                    getCategories(token)
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun updateCategory(token: String, id: Int, category: CategoryRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = categoryRepository.updateCategory(token, id, category)
                if (response.isSuccessful) {
                    getCategories(token)
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun deleteCategory(token: String, id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = categoryRepository.deleteCategory(token, id)
                if (response.isSuccessful) {
                    getCategories(token)
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun clearSelectedCategory() {
        _uiState.update { it.copy(selectedCategory = null) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
