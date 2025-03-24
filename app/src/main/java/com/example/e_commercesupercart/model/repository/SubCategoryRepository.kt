package com.example.e_commercesupercart.model.repository

import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.subcategories.SubCategoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SubCategoryRepository(private val apiService: ApiService ){

    suspend fun getSubCategories(categoryId: String): SubCategoryResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getSubCategories(categoryId)
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!
                } else {
                    SubCategoryResponse(
                        status = 1,
                        message = "Failed to fetch subcategories",
                        subcategories = emptyList()
                    )
                }
            } catch (e: Exception) {
                SubCategoryResponse(
                    status = 1,
                    message = "Error: ${e.message}",
                    subcategories = emptyList()
                )
            }
        }
    }
}