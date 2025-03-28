package com.example.e_commercesupercart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commercesupercart.model.AddAddressRequest
import com.example.e_commercesupercart.model.Address
import com.example.e_commercesupercart.model.repository.AddressRepository
import kotlinx.coroutines.launch

class AddressViewModel(private val addressRepository: AddressRepository) : ViewModel() {

    private val _addresses = MutableLiveData<List<Address>?>()
    val addresses: MutableLiveData<List<Address>?> get() = _addresses

    private val _addAddressStatus = MutableLiveData<String>()
    val addAddressStatus: LiveData<String> get() = _addAddressStatus

    init {
        _addresses.value = emptyList()
    }

    fun addAddress(userId: Int, title: String, address: String) {
        viewModelScope.launch {
            try {
                val response = addressRepository.addAddress(AddAddressRequest(userId, title, address))
                if (response != null) {
                    if (response.status == 0) {
                        _addAddressStatus.value = "Address added successfully"
                        getAddresses(userId)
                    } else {
                        _addAddressStatus.value = "Failed to add address"
                    }
                }
            } catch (e: Exception) {
                _addAddressStatus.value = "Error: ${e.message}"
            }
        }
    }

    fun getAddresses(userId: Int) {
        viewModelScope.launch {
            try {
                val response = addressRepository.getAddresses(userId)
                if (response != null) {
                    if (response.status == 0) {
                        _addresses.value = response.addresses
                    } else {
                        _addresses.value = emptyList()
                    }
                }
            } catch (e: Exception) {
                _addresses.value = emptyList()
            }
        }
    }
}


class AddressViewModelFactory(
    private val addressRepository: AddressRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddressViewModel::class.java)) {
            return AddressViewModel(addressRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
