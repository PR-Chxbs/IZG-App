package com.prince.izg.data.repository

import com.prince.izg.data.remote.api.StockApi
import com.prince.izg.data.remote.dto.Stock.StockRequest
import com.prince.izg.data.remote.dto.Stock.StockResponse
import retrofit2.Response

class StockRepository(
    private val stockApi: StockApi
) {

    suspend fun addStock(token: String, stock: StockRequest): Response<StockResponse> {
        return stockApi.addStock("Bearer $token", stock)
    }

    suspend fun getStockItems(): Response<List<StockResponse>> {
        return stockApi.getStockItems()
    }

    suspend fun getStockItemById(id: Int): Response<StockResponse> {
        return stockApi.getStockItemById(id)
    }

    suspend fun updateStockItem(token: String, id: Int, stock: StockRequest): Response<StockResponse> {
        return stockApi.updateStockItemById("Bearer $token", id, stock)
    }

    suspend fun deleteStockItem(token: String, id: Int): Response<StockResponse> {
        return stockApi.deleteStockItemById("Bearer $token", id)
    }
}
