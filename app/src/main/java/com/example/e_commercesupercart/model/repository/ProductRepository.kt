package com.example.e_commercesupercart.model.repository

import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.subcategories.Product
import com.example.e_commercesupercart.model.subcategories.ProductResponse
import retrofit2.Response

class ProductRepository(private val apiService: ApiService) {
    suspend fun getProductBySubCategory(subCategoryId: String): Response<ProductResponse>{
        return apiService.getProductsBySubcategory(subCategoryId)

    }
}