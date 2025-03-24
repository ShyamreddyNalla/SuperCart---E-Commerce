package com.example.e_commercesupercart.model.repository

import com.example.e_commercesupercart.model.ApiClient
import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.LoginRequest
import com.example.e_commercesupercart.model.LoginResponse
import retrofit2.Response

class LoginRepository {
    private val apiService: ApiService = ApiClient.retrofit.create(ApiService::class.java)

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse> {
        return apiService.loginUser(loginRequest)
    }
}