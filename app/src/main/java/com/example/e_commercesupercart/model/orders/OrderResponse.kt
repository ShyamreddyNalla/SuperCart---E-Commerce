package com.example.e_commercesupercart.model.orders

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("order_id")
    val orderId: Int? = null
)
