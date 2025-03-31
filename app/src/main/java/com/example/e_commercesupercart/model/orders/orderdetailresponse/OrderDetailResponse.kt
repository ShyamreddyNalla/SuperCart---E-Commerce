package com.example.e_commercesupercart.model.orders.orderdetailresponse

import com.google.gson.annotations.SerializedName

data class OrderDetailsResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("order")
    val order: Order,
    @SerializedName("status")
    val status: Int
)