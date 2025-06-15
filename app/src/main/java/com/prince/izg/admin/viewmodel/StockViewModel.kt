package com.prince.izg.admin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.izg.data.remote.dto.Stock.StockRequest
import com.prince.izg.data.remote.dto.Stock.StockResponse
import com.prince.izg.data.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StockUiState(
    val stockItems: List<StockResponse> = emptyList(),
    val selectedStockItem: StockResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class StockViewModel(
    private val stockRepository: StockRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StockUiState())
    val uiState: StateFlow<StockUiState> = _uiState

    fun getAllStock(token: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = stockRepository.getStockItems()
                if (response.isSuccessful) {
                    _uiState.update { it.copy(stockItems = response.body() ?: emptyList(), isLoading = false) }
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun getStockById(token: String, id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = stockRepository.getStockItemById(id)
                if (response.isSuccessful) {
                    _uiState.update { it.copy(selectedStockItem = response.body(), isLoading = false) }
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun addStock(token: String, stock: StockRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = stockRepository.addStock(token, stock)
                if (response.isSuccessful) {
                    getAllStock(token)
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun updateStock(token: String, id: Int, stock: StockRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = stockRepository.updateStockItem(token, id, stock)
                if (response.isSuccessful) {
                    getAllStock(token)
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun deleteStock(token: String, id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = stockRepository.deleteStockItem(token, id)
                if (response.isSuccessful) {
                    getAllStock(token)
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun clearSelectedStock() {
        _uiState.update { it.copy(selectedStockItem = null) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
