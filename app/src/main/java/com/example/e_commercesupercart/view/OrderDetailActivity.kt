package com.example.e_commercesupercart.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.e_commercesupercart.databinding.ActivityOrderDetailBinding
import com.example.e_commercesupercart.model.ApiClient
import com.example.e_commercesupercart.model.orders.orderdetailresponse.OrderDetailsResponse
import com.example.e_commercesupercart.model.repository.OrderDetailsRepository
import com.example.e_commercesupercart.viewmodel.OrderDetailsViewModel
import com.example.e_commercesupercart.viewmodel.OrderDetailsViewModelFactory


class OrderDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding
    private val orderDetailsViewModel: OrderDetailsViewModel by viewModels {
        OrderDetailsViewModelFactory(OrderDetailsRepository(ApiClient.apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var orderId = intent.getStringExtra("order_id")

        if (orderId == null) {
            val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
            orderId = sharedPreferences.getString("order_id", null)
        }

        Log.d("OrderDetailsActivity", "Order ID: $orderId")

        if (orderId != null) {
            orderDetailsViewModel.fetchOrderDetails(orderId)
        } else {
            Toast.makeText(this, "Order ID is missing", Toast.LENGTH_SHORT).show()
            finish()
        }

        orderDetailsViewModel.orderDetails.observe(this, Observer { orderDetails ->
            orderDetails?.let {
                displayOrderDetails(it)
            }
        })

        binding.btnCancelOrder.setOnClickListener {
            Toast.makeText(this, "Order cancelled", Toast.LENGTH_SHORT).show()
        }
    }


    private fun displayOrderDetails(orderDetails: OrderDetailsResponse) {
        binding.tvOrderId.text = "Order ID: ${orderDetails.order?.order_id}"
        binding.tvOrderStatus.text = "Status: ${orderDetails.order?.order_status}"
        binding.tvTotalAmount.text = "Total: ₹${orderDetails.order?.bill_amount}"
        binding.tvAddressTitle.text =  "Address Title: ${orderDetails.order?.address_title}"
        binding.tvDeliveryAddressValue.text = "Delivery Address: ${orderDetails.order?.address }"
        binding.tvPaymentMethodValue.text =  "${orderDetails.order?.payment_method}"
    }
}
