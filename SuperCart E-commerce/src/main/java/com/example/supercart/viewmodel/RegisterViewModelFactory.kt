package com.example.supercart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.supercart.model.repository.RegisterRepository

class RegisterViewModelFactory(private val registerRepository: RegisterRepository):ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(registerRepository) as T
    }
}