package com.example.e_commercesupercart.model

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("address_id")
    val addressId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("address")
    val address: String
)

data class AddressResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("addresses")
    val addresses: List<Address>?
)

data class AddAddressRequest(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("address")
    val address: String
)

data class AddAddressResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String
)
