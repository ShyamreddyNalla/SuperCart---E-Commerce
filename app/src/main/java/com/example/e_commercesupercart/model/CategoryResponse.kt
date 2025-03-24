package com.example.e_commercesupercart.model
import com.google.gson.annotations.SerializedName

data class CategoryResponse(

    @SerializedName("status")
    val status:Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("categories")
    val categories: List<Category>
)
