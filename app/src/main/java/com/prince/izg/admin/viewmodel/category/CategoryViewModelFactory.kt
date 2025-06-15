package com.prince.izg.admin.viewmodel.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.izg.data.repository.CategoryRepository

class CategoryViewModelFactory(
    private val categoryRepository: CategoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(categoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
