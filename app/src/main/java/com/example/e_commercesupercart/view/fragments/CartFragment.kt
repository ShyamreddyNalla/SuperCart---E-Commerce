package com.example.e_commercesupercart.view.fragments

import CartAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercesupercart.R
import com.example.e_commercesupercart.databinding.FragmentCartBinding
import com.example.e_commercesupercart.model.roomdb.CartDatabase
import com.example.e_commercesupercart.model.roomdb.CartRepository
import com.example.e_commercesupercart.viewmodel.CartViewModel
import com.example.e_commercesupercart.viewmodel.CartViewModelFactory

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        val repository = CartRepository(CartDatabase.getDatabase(requireContext()).cartDao())
        cartViewModel = ViewModelProvider(this, CartViewModelFactory(repository))[CartViewModel::class.java]

        setupRecyclerView()

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            cartAdapter.updateCartItems(cartItems)
        }

        binding.buttonCheckout.setOnClickListener {

            Toast.makeText(requireContext(), "Proceeding to checkout", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(emptyList(), { cartItem, quantity ->
            cartViewModel.addItem(cartItem)
        }, { cartItem ->
            cartViewModel.removeItem(cartItem)
        })

        binding.recyclerCart.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }
}
