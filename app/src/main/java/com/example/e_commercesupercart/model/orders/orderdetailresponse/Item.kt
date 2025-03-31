package com.example.e_commercesupercart.model.orders.orderdetailresponse

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("product_id")
    val product_id: String,
    @SerializedName("product_image_url")
    val product_image_url: String,
    @SerializedName("product_name")
    val product_name: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("unit_price")
    val unit_price: String
)