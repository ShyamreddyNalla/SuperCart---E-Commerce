package com.example.e_commercesupercart.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commercesupercart.model.product_details.Product
import com.example.e_commercesupercart.model.repository.ProductDetailRepository
import kotlinx.coroutines.launch

class ProductDetailsViewModel (private val repository: ProductDetailRepository): ViewModel(){

    private val _productDetails = MutableLiveData<Product?>()
    val productDetails: LiveData<Product?> = _productDetails

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchProductDetails(productId: String){
        viewModelScope.launch {
            val response = repository.getProductDetails(productId)
            if(response?.status == 0){
                _productDetails.value = response.product
            }else{
                _errorMessage.value = response?.message?: "Failed to load Product details"
            }
        }
    }
}

class ProductDetailsViewModelFactory(private val repository: ProductDetailRepository ): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailsViewModel(repository) as T
    }
}