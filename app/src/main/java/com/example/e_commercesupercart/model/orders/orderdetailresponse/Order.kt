package com.example.e_commercesupercart.model.orders.orderdetailresponse

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("address")
    val address: String,
    @SerializedName("address_title")
    val address_title: String,
    @SerializedName("bill_amount")
    val bill_amount: String,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("order_date")
    val order_date: String,
    @SerializedName("order_id")
    val order_id: String,
    @SerializedName("order_status")
    val order_status: String,
    @SerializedName("payment_method")
    val payment_method: String
)