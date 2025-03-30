package com.example.e_commercesupercart.model.repository

import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.orders.OrderRequest
import com.example.e_commercesupercart.model.orders.OrderResponse

class OrderRepository(private val apiService: ApiService) {
    suspend fun placeOrder(orderRequest: OrderRequest): OrderResponse? {
        val response = apiService.placeOrder(orderRequest)
        return if (response.isSuccessful) response.body() else null
    }
}
