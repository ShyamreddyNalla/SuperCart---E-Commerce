package com.example.e_commercesupercart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commercesupercart.model.product_details.Product
import com.example.e_commercesupercart.model.roomdb.CartItem
import com.example.e_commercesupercart.model.roomdb.CartRepository
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    val cartItems: LiveData<List<CartItem>> = repository.allCartItems

    fun addItem(item: CartItem) {
        repository.addItem(item)
    }

    fun removeItem(item: CartItem) {
        repository.removeItem(item)
    }
    fun getProductCount(productId: Int): LiveData<Int> {
        return repository.getProductCount(productId)
    }


}


class CartViewModelFactory(private val repository: CartRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}