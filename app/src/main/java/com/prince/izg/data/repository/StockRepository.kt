package com.prince.izg.data.repository

import com.prince.izg.data.remote.api.StockApi
import com.prince.izg.data.remote.dto.Stock.StockRequest
import com.prince.izg.data.remote.dto.Stock.StockResponse
import javax.inject.Inject

class StockRepository @Inject constructor(
    private val stockApi: StockApi
) {

    suspend fun addStock(token: String, stock: StockRequest): StockResponse {
        return stockApi.addStock("Bearer $token", stock)
    }

    suspend fun getStockItems(): List<StockResponse> {
        return stockApi.getStockItems()
    }

    suspend fun getStockItemById(id: Int): StockResponse {
        return stockApi.getStockItemById(id)
    }

    suspend fun updateStockItem(token: String, id: Int, stock: StockRequest): StockResponse {
        return stockApi.updateStockItemById("Bearer $token", id, stock)
    }

    suspend fun deleteStockItem(token: String, id: Int): StockResponse {
        return stockApi.deleteStockItemById("Bearer $token", id)
    }
}
