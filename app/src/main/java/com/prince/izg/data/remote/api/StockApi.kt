package com.prince.izg.data.remote.api

import com.prince.izg.data.remote.dto.Stock.StockRequest
import com.prince.izg.data.remote.dto.Stock.StockResponse
import retrofit2.Response
import retrofit2.http.*

interface StockApi {

    @POST("stock")
    suspend fun addStock(
        @Header("Authorization") token: String,
        @Body stock: StockRequest
    ): Response<StockResponse>

    @GET("stock")
    suspend fun getStockItems(): Response<List<StockResponse>>

    @GET("stock/{id}")
    suspend fun getStockItemById(
        @Path("id") id: Int
    ): Response<StockResponse>

    @PUT("stock/{id}")
    suspend fun updateStockItemById(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body stock: StockRequest
    ): Response<StockResponse>

    @DELETE("stock/{id}")
    suspend fun deleteStockItemById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<StockResponse>
}
