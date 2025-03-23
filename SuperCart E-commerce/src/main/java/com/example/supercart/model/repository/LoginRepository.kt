package com.example.supercart.model.repository

import com.example.supercart.model.LoginRequest
import com.example.supercart.model.LoginResponse
import com.example.supercart.model.clients.ApiClient
import com.example.supercart.model.clients.ApiService
import retrofit2.Response

class LoginRepository {
    private val apiService: ApiService = ApiClient.retrofit.create(ApiService::class.java)

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse> {
        return apiService.loginUser(loginRequest)
    }
}