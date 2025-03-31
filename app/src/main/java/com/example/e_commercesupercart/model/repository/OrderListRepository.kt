package com.example.e_commercesupercart.model.repository

import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.orders.OrderListResponse
import retrofit2.Response

class OrderListRepository(private val apiService: ApiService) {

    suspend fun getUserOrders(userId: String): Response<OrderListResponse> {
        return apiService.getUserOrders(userId)
    }
}
