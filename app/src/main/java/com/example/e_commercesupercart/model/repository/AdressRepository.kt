package com.example.e_commercesupercart.model.repository

import com.example.e_commercesupercart.model.AddAddressRequest
import com.example.e_commercesupercart.model.AddAddressResponse
import com.example.e_commercesupercart.model.AddressResponse
import com.example.e_commercesupercart.model.ApiService

class AddressRepository(private val apiService: ApiService) {

    suspend fun getAddresses(userId: Int): AddressResponse? {
        val response = apiService.getAddresses(userId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun addAddress(addAddressRequest: AddAddressRequest): AddAddressResponse? {
        val response = apiService.addAddress(addAddressRequest)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}
