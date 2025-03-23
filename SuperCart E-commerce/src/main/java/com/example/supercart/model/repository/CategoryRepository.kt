package com.example.supercart.model.repository

import com.example.supercart.model.CategoryResponse
import com.example.supercart.model.clients.ApiService
import retrofit2.Response

class CategoryRepository(private val apiService: ApiService){

    suspend fun getCategories(): Response<CategoryResponse> {
      return apiService.getCategories()
    }

}
