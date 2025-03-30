package com.example.e_commercesupercart.model.orders

import com.google.gson.annotations.SerializedName

data class OrderItem(
    @SerializedName("product_id") val productId: Int,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("unit_price") val unitPrice: Int
)