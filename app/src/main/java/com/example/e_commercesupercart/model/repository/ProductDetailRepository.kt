package com.example.e_commercesupercart.model.repository

import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.product_details.ProductDetailsResponse

class ProductDetailRepository(private val apiService: ApiService) {
  suspend fun getProductDetails(productId: String): ProductDetailsResponse?{
      return try {
      val response = apiService.getProductDetails(productId)
  if (response.isSuccessful) response.body() else null
   }catch (e: Exception){
      null

      }
  }
}