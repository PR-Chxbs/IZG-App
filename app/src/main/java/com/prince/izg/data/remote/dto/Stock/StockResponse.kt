package com.prince.izg.data.remote.dto.Stock

data class StockResponse(
    val id: Int,
    val category_id: Int,
    val name: String,
    val quantity: Int,
    val image: String?,
    val updated_at: String,
    val created_at: String
)