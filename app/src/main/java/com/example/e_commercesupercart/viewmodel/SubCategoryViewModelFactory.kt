package com.example.e_commercesupercart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_commercesupercart.model.repository.SubCategoryRepository

class SubCategoryViewModelFactory(private val subCategoryRepository: SubCategoryRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SubCategoryViewModel(subCategoryRepository) as T
    }
}