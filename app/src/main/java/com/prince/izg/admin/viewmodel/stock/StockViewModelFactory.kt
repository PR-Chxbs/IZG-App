package com.prince.izg.admin.viewmodel.stock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.izg.data.repository.StockRepository

class StockViewModelFactory(
    private val stockRepository: StockRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockViewModel::class.java)) {
            return StockViewModel(stockRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
