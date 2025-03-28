package com.example.e_commercesupercart.view.fragments

import CartAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercesupercart.R
import com.example.e_commercesupercart.databinding.FragmentCartBinding
import com.example.e_commercesupercart.model.roomdb.CartDatabase
import com.example.e_commercesupercart.model.roomdb.CartItem
import com.example.e_commercesupercart.model.roomdb.CartRepository
import com.example.e_commercesupercart.view.CheckoutActivity
import com.example.e_commercesupercart.view.MainActivity
import com.example.e_commercesupercart.viewmodel.CartViewModel
import com.example.e_commercesupercart.viewmodel.CartViewModelFactory
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        val repository = CartRepository(CartDatabase.getDatabase(requireContext()).cartDao())
        cartViewModel = ViewModelProvider(this, CartViewModelFactory(repository))[CartViewModel::class.java]

        setupToolbar()
        setupRecyclerView()
        observeCartItems()

        binding.btnCheckout.setOnClickListener {
            Toast.makeText(requireContext(), "Proceeding to checkout", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), CheckoutActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }

    private fun setupToolbar() {
        binding.cartToolbar.setNavigationOnClickListener {
            val categoriesFragment = parentFragmentManager.findFragmentByTag("CategoriesFragment") as? CategoriesFragment
            categoriesFragment?.binding?.drawerLayout?.openDrawer(GravityCompat.START)
        }

        binding.cartToolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_search) {
                Toast.makeText(requireContext(), "Search clicked", Toast.LENGTH_SHORT).show()
                true
            } else {
                false
            }
        }
    }


    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(emptyList(), { cartItem, quantity ->
            cartViewModel.addItem(cartItem.copy(quantity = quantity))
        }, { cartItem ->
            cartViewModel.removeItem(cartItem)
        })

        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    private fun observeCartItems() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            cartAdapter.updateCartItems(cartItems)
            updateTotalPrice(cartItems)
        }
    }

    private fun updateTotalPrice(cartItems: List<CartItem>) {
        val totalPrice = cartItems.sumOf { it.productPrice.toDouble() * it.quantity }
        binding.tvTotalBill.text = "$$totalPrice"
    }

}
