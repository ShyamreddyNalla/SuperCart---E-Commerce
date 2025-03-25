package com.example.e_commercesupercart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercesupercart.model.repository.SearchRepository
import com.example.e_commercesupercart.model.subcategories.ProductResponse
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository):ViewModel() {

    private val _searchResults = MutableLiveData<ProductResponse?>()
    val searchResults: LiveData<ProductResponse?> get() = _searchResults


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun searchProducts(query:String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.searchProducts(query)
               if(response.isSuccessful && response.body()!=null){
                   _searchResults.value=response.body()
               }else{
                   _searchResults.value = null
               }

        }catch (e: Exception){
            _searchResults.value = null
        }finally {
            _isLoading.value = false
        }
        }
    }
}