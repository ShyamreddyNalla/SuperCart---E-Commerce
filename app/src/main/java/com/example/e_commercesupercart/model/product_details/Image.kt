package com.example.e_commercesupercart.model.product_details

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("image") val image: String,
    @SerializedName("display_order") val displayOrder: String
)