package com.example.e_commercesupercart.model.subcategories

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String,
  @SerializedName("products")
    val products: List<Product>
    )