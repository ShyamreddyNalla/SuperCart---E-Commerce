package com.example.e_commercesupercart.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_commercesupercart.model.repository.CategoryRepository
import com.example.e_commercesupercart.viewmodel.CategoryViewModel


class CategoryViewModelFactory(private val repository: CategoryRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(repository) as T
    }

}