package com.example.e_commercesupercart.view.adaptors

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commercesupercart.databinding.ProductItemBinding
import com.example.e_commercesupercart.model.subcategories.Product


class ProductViewHolder(private val binding: ProductItemBinding, private val onProductClick: (String) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product) {
        binding.productName.text = product.productName
        binding.productPrice.text = "$${product.price}"
        binding.productDescription.text = product.description
        Glide.with(binding.imageProduct.context)
            .load("https://apolisrises.co.in/myshop/images/${product.productImageUrl}")
            .into(binding.imageProduct)

        val rating = product.averageRating.toFloatOrNull()?: 0f
        binding.ratingBar.rating = rating

        binding.root.setOnClickListener{
            onProductClick(product.productId)
        }
    }
}

