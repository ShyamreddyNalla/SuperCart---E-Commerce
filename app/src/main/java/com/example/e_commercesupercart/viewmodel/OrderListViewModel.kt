package com.example.e_commercesupercart.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commercesupercart.model.orders.OrderDetails
import com.example.e_commercesupercart.model.repository.OrderListRepository
import com.example.e_commercesupercart.model.orders.OrderListResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class OrderListViewModel(private val orderListRepository: OrderListRepository) : ViewModel() {

    val _orders = MutableLiveData<List<OrderDetails>>()
    val orders: LiveData<List<OrderDetails>> = _orders

     fun getUserOrders(userId: String) {
         viewModelScope.launch {
             _orders.postValue(orderListRepository.getUserOrders(userId).body().let { it?.orders })

         }
     //   Log.d("OrderListViewModel", "Fetching orders for user ID: ${orderListRepository.getUserOrders(userId)}")
        //return orderListRepository.getUserOrders(userId)
    }
}


class OrderListViewModelFactory(private val repository: OrderListRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderListViewModel(repository) as T
    }
}