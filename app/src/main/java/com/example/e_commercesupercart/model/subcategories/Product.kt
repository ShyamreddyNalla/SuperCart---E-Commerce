package com.example.e_commercesupercart.model.subcategories

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("product_id")
    val productId: String,

    @SerializedName("product_name")
    val productName: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("sub_category_id")
    val subcategoryId: String,
    @SerializedName("subcategory_name")
    val subcategoryName: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("average_rating")
    val averageRating: String,

    @SerializedName("product_image_url")
    val productImageUrl: String
)
