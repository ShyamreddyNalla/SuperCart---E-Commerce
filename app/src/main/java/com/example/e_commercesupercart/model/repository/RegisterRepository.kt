package com.example.e_commercesupercart.model.repository

import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.RegisterRequest
import com.example.e_commercesupercart.model.RegisterResponse
import retrofit2.Response

class RegisterRepository(private val apiService: ApiService) {
    suspend fun registerUser(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return apiService.registerUser(registerRequest)
    }
}