package com.example.e_commercesupercart.view.adaptors

import com.example.e_commercesupercart.databinding.CartItemsLayoutBinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercesupercart.model.roomdb.CartItem

class CartItemsAdapter : RecyclerView.Adapter<CartItemsAdapter.CartViewHolder>() {

    private var cartItems: List<CartItem> = listOf()

    inner class CartViewHolder(private val binding: CartItemsLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItem) {
            binding.tvProductName.text = cartItem.productName
            binding.tvQuantity.text = "Qty: ${cartItem.quantity}"
            binding.tvUnitPrice.text = "₹${cartItem.productPrice}"
            binding.tvTotalAmount.text = "₹${cartItem.productPrice.toDouble() * cartItem.quantity}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size

    fun submitList(items: List<CartItem>) {
        cartItems = items
        notifyDataSetChanged()
    }
}
