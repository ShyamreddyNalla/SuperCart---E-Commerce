package com.example.e_commercesupercart.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercesupercart.view.adaptors.OrderListAdapter
import com.example.e_commercesupercart.databinding.ActivityOrderListBinding
import com.example.e_commercesupercart.model.ApiClient
import com.example.e_commercesupercart.model.orders.OrderDetails
import com.example.e_commercesupercart.model.repository.OrderListRepository
import com.example.e_commercesupercart.view.OrderDetailsActivity
import com.example.e_commercesupercart.viewmodel.OrderListViewModel
import com.example.e_commercesupercart.viewmodel.OrderListViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class OrderListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderListBinding
    private lateinit var orderListViewModel: OrderListViewModel
   // private lateinit var orderListAdapter: OrderListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = OrderListViewModelFactory(OrderListRepository(ApiClient.apiService))
        orderListViewModel = ViewModelProvider(this, factory).get(OrderListViewModel::class.java)
        setUpObserver()
        orderListViewModel.getUserOrders(userId = "1")

        //binding.recyclerViewOrders.adapter = orderListAdapter

        //fetchUserOrders("1")
    }

    private fun setUpObserver() {
        orderListViewModel.orders.observe(this) { orders ->
            binding.recyclerViewOrders.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewOrders.adapter = OrderListAdapter(orders)
        }

        /*  private fun fetchUserOrders(userId: String) {
          lifecycleScope.launch {
              val response = withContext(Dispatchers.IO) {
                  orderListViewModel.getUserOrders(userId)
              }

              if (response.isSuccessful) {
                  val orderListResponse = response.body()
                  if (orderListResponse != null && orderListResponse.status == 0) {
                      Log.d("OrderListActivity", "Orders: ${orderListResponse.orders}")
                      val orders = orderListResponse.orders ?: emptyList()

                      orderListAdapter = OrderListAdapter(orders) { order ->
                          openOrderDetails(order)
                      }
                      binding.recyclerViewOrders.adapter = orderListAdapter
                  } else {
                      showError(orderListResponse?.message ?: "Unknown error")
                  }
              } else {
                  showError(response.message() ?: "Failed to fetch orders")
              }
          }

  }
      private fun showError(message: String) {
          Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
      }*/


      /*  private fun openOrderDetails(order: OrderDetails) {
            val intent = Intent(this, OrderDetailsActivity::class.java)
            intent.putExtra("order_id", order.orderId)
            startActivity(intent)
        }*/
    }
}
