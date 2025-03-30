package com.example.e_commercesupercart.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.e_commercesupercart.model.orders.orderdetailresponse.OrderDetailsResponse
import com.example.e_commercesupercart.model.repository.OrderDetailsRepository
import com.example.e_commercesupercart.model.repository.OrderRepository
import kotlinx.coroutines.launch


class OrderDetailsViewModel(private val orderDetailsRepository: OrderDetailsRepository) : ViewModel() {

    private val _orderDetails = MutableLiveData<OrderDetailsResponse>()
    val orderDetails: LiveData<OrderDetailsResponse> get() = _orderDetails

    fun fetchOrderDetails(orderId: String) {
        viewModelScope.launch {
            try {
                val response = orderDetailsRepository.getOrderDetails(orderId)
           if (response.isSuccessful && response.body() != null) {
                    Log.d("OrderDetailsViewModel", "Response: ${response.body()}")
                    _orderDetails.value = response.body()
                } else {
                    Log.e("OrderDetailsViewModel", "Error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("OrderDetailsViewModel", "Exception: ${e.message}")
            }
        }
    }
}
class OrderDetailsViewModelFactory(private val orderDetailsRepository: OrderDetailsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return OrderDetailsViewModel(orderDetailsRepository) as T
    }
}

