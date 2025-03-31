package com.example.e_commercesupercart.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_commercesupercart.model.roomdb.CartItem


class CheckoutViewModel : ViewModel() {
    var selectedDeliveryAddress = MutableLiveData<String?>()
    var selectedDeliveryAddressTitle = MutableLiveData<String?>()
    val selectedPaymentMethod = MutableLiveData<String?>()

}

