package com.example.e_commercesupercart.model

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,
    val user: User
)
