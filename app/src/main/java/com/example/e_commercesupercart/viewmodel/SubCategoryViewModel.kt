package com.example.e_commercesupercart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercesupercart.model.repository.SubCategoryRepository
import com.example.e_commercesupercart.model.subcategories.Subcategory
import kotlinx.coroutines.launch

class SubCategoryViewModel(private val repository: SubCategoryRepository): ViewModel() {
    private val _subCategoriesLiveData = MutableLiveData<List<Subcategory>>()
    val subCategoriesLiveData: LiveData<List<Subcategory>> = _subCategoriesLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    fun getSubcategories(categoryId: String) {
        viewModelScope.launch {
            val response = repository.getSubCategories(categoryId)

            if (response.status == 0) {
                _subCategoriesLiveData.postValue(response.subcategories)
            } else {
                _errorLiveData.postValue(response.message)
            }
        }
    }
}