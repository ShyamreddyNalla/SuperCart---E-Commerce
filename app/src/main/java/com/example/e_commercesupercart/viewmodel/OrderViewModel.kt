package com.example.e_commercesupercart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_commercesupercart.model.orders.OrderRequest
import com.example.e_commercesupercart.model.orders.OrderResponse
import com.example.e_commercesupercart.model.repository.OrderRepository

class OrderViewModel(private val orderRepository: OrderRepository) : ViewModel() {

    suspend fun placeOrder(orderRequest: OrderRequest): OrderResponse? {
        return orderRepository.placeOrder(orderRequest)
    }
}

class OrderViewModelFactory(private val orderRepository: OrderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            return OrderViewModel(orderRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
