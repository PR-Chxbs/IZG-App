package com.prince.izg.data.remote.api

import com.prince.izg.data.remote.dto.Category.CategoryRequest
import com.prince.izg.data.remote.dto.Category.CategoryResponse
import retrofit2.Response
import retrofit2.http.*

interface CategoryApi {

    @GET("categories")
    suspend fun getCategories(): Response<List<CategoryResponse>>

    @GET("categories/{id}")
    suspend fun getCategoryById(
        @Path("id") id: Int
    ): Response<CategoryResponse>

    @POST("categories")
    suspend fun addCategory(
        @Body request: CategoryRequest
    ): Response<CategoryResponse>

    @PUT("categories/{id}")
    suspend fun updateCategory(
        @Path("id") id: Int,
        @Body request: CategoryRequest
    ): Response<CategoryResponse>

    @DELETE("categories/{id}")
    suspend fun deleteCategory(
        @Path("id") id: Int
    ): Response<Unit>
}
