package com.example.e_commercesupercart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercesupercart.model.LoginRequest
import com.example.e_commercesupercart.model.LoginResponse
import com.example.e_commercesupercart.model.repository.LoginRepository

import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse?>()
    val loginResponse: LiveData<LoginResponse?> = _loginResponse

    fun loginUser(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)

        viewModelScope.launch {
            val response: Response<LoginResponse> = repository.loginUser(loginRequest)
            if (response.isSuccessful) {
                _loginResponse.postValue(response.body())
            } else {
                _loginResponse.postValue(null)
            }
        }
    }
}

