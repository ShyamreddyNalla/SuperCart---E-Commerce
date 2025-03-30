package com.example.e_commercesupercart.model.orders

import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("delivery_address") val deliveryAddress: Map<String, String>,
    @SerializedName("items") val items: List<OrderItem>,
    @SerializedName("bill_amount") val billAmount: Int,
    @SerializedName("payment_method") val paymentMethod: String
)

data class DeliveryAddress(
    @SerializedName("title") val title: String,
    @SerializedName("address") val address: String
)