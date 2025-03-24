package com.example.e_commercesupercart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercesupercart.model.Category
import com.example.e_commercesupercart.model.repository.CategoryRepository


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository): ViewModel() {

    private val _categoriesLiveData = MutableLiveData<List<Category>>()
    val categoriesLiveData: LiveData<List<Category>> = _categoriesLiveData

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = repository.getCategories()
            _categoriesLiveData.postValue(categories)
        }
    }

}
