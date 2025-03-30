package com.example.e_commercesupercart.model.repository

import com.example.e_commercesupercart.model.ApiClient.apiService
import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.orders.orderdetailresponse.OrderDetailsResponse
import retrofit2.Response

class OrderDetailsRepository (private val apiService: ApiService){
    suspend fun getOrderDetails(orderId: String): Response<OrderDetailsResponse> {
        return apiService.getOrderDetails(orderId)
    }
}