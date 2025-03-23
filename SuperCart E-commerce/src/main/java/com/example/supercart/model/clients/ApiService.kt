package com.example.supercart.model.clients

import com.example.supercart.model.CategoryResponse
import com.example.supercart.model.LoginRequest
import com.example.supercart.model.LoginResponse
import com.example.supercart.model.RegisterRequest
import com.example.supercart.model.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("User/auth")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("User/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>


    @GET("Category")
    suspend fun getCategories(): Response<CategoryResponse>
}