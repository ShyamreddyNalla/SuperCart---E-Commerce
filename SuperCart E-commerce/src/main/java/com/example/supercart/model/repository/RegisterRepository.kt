package com.example.supercart.model.repository

import com.example.supercart.model.RegisterRequest
import com.example.supercart.model.RegisterResponse
import com.example.supercart.model.clients.ApiService
import retrofit2.Response

class RegisterRepository(private val apiService: ApiService) {
    suspend fun registerUser(registerRequest: RegisterRequest): Response<RegisterResponse>{
        return apiService.registerUser(registerRequest)
    }
}