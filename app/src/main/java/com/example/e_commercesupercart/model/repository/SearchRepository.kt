package com.example.e_commercesupercart.model.repository

import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.subcategories.ProductResponse
import retrofit2.Response

class SearchRepository(private val apiService: ApiService) {
    suspend fun searchProducts(query:String): Response<ProductResponse>{
        return apiService.searchProducts(query)
}}