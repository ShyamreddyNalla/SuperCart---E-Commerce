package com.example.e_commercesupercart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_commercesupercart.model.repository.RegisterRepository

class RegisterViewModelFactory(private val registerRepository: RegisterRepository):ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(registerRepository) as T
    }
}