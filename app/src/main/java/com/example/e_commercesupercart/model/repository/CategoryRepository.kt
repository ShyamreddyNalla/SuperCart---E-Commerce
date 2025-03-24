package com.example.e_commercesupercart.model.repository
import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.Category


class CategoryRepository(private val apiService: ApiService) {

    suspend fun getCategories(): List<Category> {
        val response = apiService.getCategories()
        return if (response.isSuccessful && response.body() != null) {
            response.body()?.categories ?: emptyList()
        } else {
            emptyList()
        }
    }

}
