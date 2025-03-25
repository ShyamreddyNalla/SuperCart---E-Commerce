package com.example.e_commercesupercart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commercesupercart.model.repository.SearchRepository
import com.example.e_commercesupercart.model.subcategories.ProductResponse
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository): ViewModel() {

    private val _searchResults = MutableLiveData<ProductResponse?>()
    val searchResult: LiveData<ProductResponse?> get() = _searchResults

    fun searchProducts(query: String){
        viewModelScope.launch {
        try {
            val response = repository.searchProducts(query)
            if (response.isSuccessful && response.body()!= null){
                _searchResults.value = response.body()
            }else{
                _searchResults.value = null
            }
        }catch (e:Exception){
            _searchResults.value = null
        }}
    }
}

class SearchViewModelFactory(private val repository: SearchRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(repository) as T
    }
}