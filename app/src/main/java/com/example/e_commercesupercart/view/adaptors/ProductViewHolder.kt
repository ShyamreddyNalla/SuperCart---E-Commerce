package com.example.e_commercesupercart.view.adaptors

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commercesupercart.databinding.ProductItemBinding
import com.example.e_commercesupercart.model.roomdb.CartItem
import com.example.e_commercesupercart.model.subcategories.Product
import com.example.e_commercesupercart.viewmodel.CartViewModel


class ProductViewHolder(private val binding: ProductItemBinding,
                        private val onProductClick: (String) -> Unit,
                        private val cartViewModel: CartViewModel) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(product: Product) {
        binding.productName.text = product.productName
        binding.productPrice.text = "$${product.price}"
        binding.productDescription.text = product.description
        Glide.with(binding.imageProduct.context)
            .load("https://apolisrises.co.in/myshop/images/${product.productImageUrl}")
            .into(binding.imageProduct)

        val rating = product.averageRating.toFloatOrNull() ?: 0f
        binding.ratingBar.rating = rating

        binding.root.setOnClickListener {
            onProductClick(product.productId)
        }

        binding.buttonAddToCart.setOnClickListener {
            val cartItem = CartItem(
                productId = product.productId.toInt(),
                productName = product.productName,
                productPrice = product.price,
                productImage = (product.productImageUrl.firstOrNull() ?: "").toString(),
                quantity = 1
            )
            cartViewModel.addItem(cartItem)  // Add product to cart
            binding.buttonAddToCart.visibility = View.GONE
            binding.quantityLayout.visibility = View.VISIBLE
            binding.textQuantity.text = "1"
        }

        binding.buttonIncrease.setOnClickListener {
            val newQuantity = binding.textQuantity.text.toString().toInt() + 1
            binding.textQuantity.text = newQuantity.toString()

            val updatedItem = CartItem(
                productId = product.productId.toInt(),
                productName = product.productName,
                productPrice = product.price,
                productImage = (product.productImageUrl.firstOrNull() ?: "").toString(),
                quantity = newQuantity
            )
            cartViewModel.addItem(updatedItem)
        }

        binding.buttonDecrease.setOnClickListener {
            val currentQuantity = binding.textQuantity.text.toString().toInt()
            val newQuantity = (currentQuantity - 1).coerceAtLeast(0)

            binding.textQuantity.text = newQuantity.toString()

            val updatedItem = CartItem(
                productId = product.productId.toInt(),
                productName = product.productName,
                productPrice = product.price,
                productImage = (product.productImageUrl.firstOrNull() ?: "").toString(),
                quantity = newQuantity
            )

            if (newQuantity > 0) {
                cartViewModel.addItem(updatedItem)
            } else {
                cartViewModel.removeItem(updatedItem)
                binding.buttonAddToCart.visibility = View.VISIBLE
                binding.quantityLayout.visibility = View.GONE
            }
        }
    }
}