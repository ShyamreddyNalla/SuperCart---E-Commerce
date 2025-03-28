package com.example.e_commercesupercart.view.fragments.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercesupercart.databinding.FragmentCartTemsBinding
import com.example.e_commercesupercart.model.roomdb.CartDatabase
import com.example.e_commercesupercart.model.roomdb.CartItem
import com.example.e_commercesupercart.model.roomdb.CartRepository
import com.example.e_commercesupercart.view.CheckoutActivity
import com.example.e_commercesupercart.view.adaptors.CartItemsAdapter
import com.example.e_commercesupercart.viewmodel.CartViewModel
import com.example.e_commercesupercart.viewmodel.CartViewModelFactory

class CartItemsFragment : Fragment() {

    private lateinit var binding: FragmentCartTemsBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartTemsBinding.inflate(inflater, container, false)

        val cartRepository = CartRepository(CartDatabase.getDatabase(requireContext()).cartDao())
        cartViewModel = ViewModelProvider(this, CartViewModelFactory(cartRepository))[CartViewModel::class.java]

        setupRecyclerView()
        observeCartItems()

        binding.btnNext.setOnClickListener {
            (activity as? CheckoutActivity)?.moveToNextTab()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        cartAdapter = CartItemsAdapter()
        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    private fun observeCartItems() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.submitList(items)
            updateTotalBill(items)
        }
    }

    private fun updateTotalBill(cartItems: List<CartItem>) {
        val totalAmount = cartItems.sumOf { it.productPrice.toDouble() * it.quantity }
        binding.tvTotalBillAmount.text = "₹$totalAmount"
    }
}
