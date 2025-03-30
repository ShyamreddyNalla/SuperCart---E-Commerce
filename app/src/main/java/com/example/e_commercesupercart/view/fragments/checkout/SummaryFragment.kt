package com.example.e_commercesupercart.view.fragments.checkout

import CartAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercesupercart.R
import com.example.e_commercesupercart.databinding.FragmentSummaryBinding
import com.example.e_commercesupercart.model.ApiClient
import com.example.e_commercesupercart.model.orders.OrderItem
import com.example.e_commercesupercart.model.orders.OrderRequest
import com.example.e_commercesupercart.model.repository.OrderRepository
import com.example.e_commercesupercart.model.roomdb.CartDatabase
import com.example.e_commercesupercart.model.roomdb.CartRepository
import com.example.e_commercesupercart.view.fragments.OrderDetailsFragment
import com.example.e_commercesupercart.viewmodel.CartViewModel
import com.example.e_commercesupercart.viewmodel.CartViewModelFactory
import com.example.e_commercesupercart.viewmodel.CheckoutViewModel
import com.example.e_commercesupercart.viewmodel.OrderViewModel
import com.example.e_commercesupercart.viewmodel.OrderViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class SummaryFragment : Fragment() {

    private lateinit var binding: FragmentSummaryBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var cartAdapter: CartAdapter

    private val checkoutViewModel: CheckoutViewModel by activityViewModels()


    private var totalBillAmount: Int = 0
    private var userId: Int = 444

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cartRepository = CartRepository(cartDao = CartDatabase.getDatabase(requireContext()).cartDao())
        val cartViewModelFactory = CartViewModelFactory(cartRepository)
        cartViewModel = ViewModelProvider(this, cartViewModelFactory).get(CartViewModel::class.java)

        val orderRepository = OrderRepository(ApiClient.apiService)
        val orderViewModelFactory = OrderViewModelFactory(orderRepository)
        orderViewModel = ViewModelProvider(this, orderViewModelFactory).get(OrderViewModel::class.java)

        cartAdapter = CartAdapter(emptyList(), onQuantityChange = { _, _ -> }, onRemoveItem = {})

        binding.rvCartItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCartItems.adapter = cartAdapter

        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.updateCartItems(items)
            totalBillAmount = items.sumOf { it.quantity * it.productPrice.toDouble() }.toInt()
            binding.tvTotalAmount.text = "Total Amount: ₹$totalBillAmount"
        }

        checkoutViewModel.selectedDeliveryAddress.observe(viewLifecycleOwner) { address ->
            binding.tvDeliveryAddress.text = address ?: "No address selected"
        }

        checkoutViewModel.selectedPaymentMethod.observe(viewLifecycleOwner) { paymentMethod ->
            binding.tvPaymentMethod.text = paymentMethod ?: "No payment method selected"
        }

        binding.btnConfirmOrder.setOnClickListener {
            if (checkoutViewModel.selectedDeliveryAddress.value == null || checkoutViewModel.selectedPaymentMethod.value == null) {
                Toast.makeText(requireContext(), "Please select address and payment method", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            placeOrder()
        }
    }

    private fun placeOrder() {
        val orderItems = cartViewModel.cartItems.value?.map {
            OrderItem(
                productId = it.productId,
                quantity = it.quantity,
                unitPrice = it.productPrice.toDouble().toInt()
            )
        } ?: emptyList()

        if (orderItems.isEmpty()) {
            Toast.makeText(requireContext(), "Your cart is empty!", Toast.LENGTH_SHORT).show()
            return
        }

        val orderRequest = OrderRequest(
            userId = userId,
            deliveryAddress = mapOf(
                "title" to (checkoutViewModel.selectedDeliveryAddress.value ?: "Home"),
                "address" to (checkoutViewModel.selectedDeliveryAddress.value ?: "")
            ),
            items = orderItems,
            billAmount = totalBillAmount,
            paymentMethod = checkoutViewModel.selectedPaymentMethod.value ?: ""
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = orderViewModel.placeOrder(orderRequest)
                requireActivity().runOnUiThread {
                    if (response != null && response.status == 0) {
                        Toast.makeText(requireContext(), "Order Placed Successfully!", Toast.LENGTH_SHORT).show()

                        val orderId = response.orderId
                        navigateToOrderDetails(orderId.toString())
                    } else {
                        Toast.makeText(requireContext(), "Order Failed: ${response?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun navigateToOrderDetails(orderId: String) {
        val bundle = Bundle().apply {
            putString("order_id", orderId)
        }

        val orderDetailsFragment = OrderDetailsFragment().apply {
            arguments = bundle
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container2, orderDetailsFragment)
            .addToBackStack(null)
            .commit()
    }


}

