package com.prince.izg.data.remote.dto.Stock

data class StockRequest(
    val category_id: Int,
    val name: String,
    val quantity: Int,
    val image: String? = null
)
