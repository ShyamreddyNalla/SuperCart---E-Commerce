package com.example.e_commercesupercart.model.subcategories

import com.google.gson.annotations.SerializedName

data class Subcategory(
    @SerializedName("subcategory_id")
    val subcategoryId: String,

    @SerializedName("subcategory_name")
    val subcategoryName: String,

    @SerializedName("category_id")
    val categoryId: String,

    @SerializedName("subcategory_image_url")
    val subCategoryImageUrl: String,

    @SerializedName("is_active")
    val isActive: String
)
