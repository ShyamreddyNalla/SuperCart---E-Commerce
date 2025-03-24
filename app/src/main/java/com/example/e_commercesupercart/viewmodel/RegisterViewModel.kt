package com.example.e_commercesupercart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercesupercart.model.RegisterRequest
import com.example.e_commercesupercart.model.RegisterResponse
import com.example.e_commercesupercart.model.repository.RegisterRepository

import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(private val registerRepository: RegisterRepository): ViewModel() {

    private val _registerResponse = MutableLiveData<RegisterResponse?>()
    val registerResponse: LiveData<RegisterResponse?> = _registerResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun registerUser(fullName: String, mobileNo: String, emailId: String, registerPassword: String) {
        val registerRequest = RegisterRequest(fullName, mobileNo, emailId, registerPassword)

        viewModelScope.launch {
            try {
                val response: Response<RegisterResponse> = registerRepository.registerUser(registerRequest)
                if (response.isSuccessful) {
                    _registerResponse.postValue(response.body())
                } else {
                    _errorMessage.postValue("Registration failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error: ${e.message}")
            }
        }
    }
}
