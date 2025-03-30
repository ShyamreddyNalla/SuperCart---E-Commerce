package com.example.e_commercesupercart.model.orders.orderdetailresponse

data class OrderDetailsResponse(
    val message: String,
    val order: Order,
    val status: Int
)