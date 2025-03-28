package com.example.e_commercesupercart.model


import com.example.e_commercesupercart.model.product_details.ProductDetailsResponse
import com.example.e_commercesupercart.model.subcategories.ProductResponse
import com.example.e_commercesupercart.model.subcategories.SubCategoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @POST("User/auth")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("User/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>


    @GET("Category")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("SubCategory")
    suspend fun getSubCategories(@Query("category_id") categoryId: String): Response<SubCategoryResponse>

    @GET("SubCategory/products/{sub_category_id}")
    suspend fun getProductsBySubcategory(@Path("sub_category_id") subCategoryId: String):Response<ProductResponse>
    @GET("Product/search")
    suspend fun searchProducts(@Query("query") query: String):Response<ProductResponse>

    @GET("Product/details/{product_id}")
    suspend fun getProductDetails(@Path("product_id") productId: String): Response<ProductDetailsResponse>

    @POST("User/address")
    suspend fun addAddress(@Body addAddressRequest: AddAddressRequest): Response<AddAddressResponse>

    @GET("User/address/{user_id}")
    suspend fun getAddresses(@Path("user_id") userId: Int): Response<AddressResponse>

   }