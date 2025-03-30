package com.example.e_commercesupercart.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.e_commercesupercart.R
import com.example.e_commercesupercart.databinding.FragmentOrderDetailsBinding
import com.example.e_commercesupercart.model.ApiClient
import com.example.e_commercesupercart.model.orders.orderdetailresponse.OrderDetailsResponse

import com.example.e_commercesupercart.model.repository.OrderDetailsRepository
import com.example.e_commercesupercart.viewmodel.OrderDetailsViewModel
import com.example.e_commercesupercart.viewmodel.OrderDetailsViewModelFactory

class OrderDetailsFragment : Fragment(R.layout.fragment_order_details) {

    private lateinit var binding: FragmentOrderDetailsBinding
    private val orderDetailsViewModel: OrderDetailsViewModel by viewModels {
        OrderDetailsViewModelFactory(OrderDetailsRepository(ApiClient.apiService))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOrderDetailsBinding.bind(view)

        // Get the order ID passed from the previous fragment or activity
        val orderId = arguments?.getString("order_id")
        Log.d("OrderDetailsFragment", "Order ID: $orderId")
        if (orderId != null) {

            orderDetailsViewModel.fetchOrderDetails(orderId)
        } else {
            Toast.makeText(requireContext(), "Order ID is missing", Toast.LENGTH_SHORT).show()
            return
        }

        // Observe the order details data
        orderDetailsViewModel.orderDetails.observe(viewLifecycleOwner, Observer { orderDetails ->
            orderDetails?.let {
                displayOrderDetails(it)
            }
        })

        // Set up the cancel button action
        binding.btnCancelOrder.setOnClickListener {
            // Handle cancel action, for now just show a message
            Toast.makeText(requireContext(), "Cancel Order clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayOrderDetails(orderDetails: OrderDetailsResponse) {
        binding.tvOrderId.text = "Order ID: ${orderDetails.order?.order_id}"
        binding.tvOrderStatus.text = "Status: ${orderDetails.order?.order_status}"
        binding.tvTotalAmount.text = "Total: ₹${orderDetails.order?.bill_amount}"
        binding.tvDeliveryAddress.text = "Delivery Address: ${orderDetails.order?.address}"
        binding.tvPaymentMethod.text = "Payment Method: ${orderDetails.order?.payment_method}"

      /*  // Set up RecyclerView with adapter for purchased items
        val adapter = PurchasedItemsAdapter(orderDetails.items)
        binding.rvPurchasedItems.layoutManager = LinearLayoutManager(context)
        binding.rvPurchasedItems.adapter = adapter*/
    }
}
