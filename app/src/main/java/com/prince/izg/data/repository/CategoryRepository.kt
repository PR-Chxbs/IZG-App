package com.prince.izg.data.repository

import com.prince.izg.data.remote.api.CategoryApi
import com.prince.izg.data.remote.dto.Category.CategoryRequest
import com.prince.izg.data.remote.dto.Category.CategoryResponse
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryApi: CategoryApi
) {

    suspend fun addCategory(category: CategoryRequest): CategoryResponse {
        return categoryApi.addCategory(category)
    }

    suspend fun getCategories(): List<CategoryResponse> {
        return categoryApi.getCategories()
    }

    suspend fun getCategoryById(id: Int): CategoryResponse {
        return categoryApi.getCategoryById(id)
    }

    suspend fun updateCategory(token: String, id: Int, category: CategoryRequest): CategoryResponse {
        return categoryApi.updateCategory(id, category)
    }

    suspend fun deleteCategory(token: String, id: Int): CategoryResponse {
        return categoryApi.deleteCategory(id)
    }
}
