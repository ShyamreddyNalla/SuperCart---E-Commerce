package com.example.e_commercesupercart.view.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commercesupercart.databinding.ProductItemBinding
import com.example.e_commercesupercart.model.subcategories.Product

class ProductAdapter(private var products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }

    class ProductViewHolder(private val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productName.text = product.productName
            binding.productPrice.text = "$${product.price}"
            binding.productDescription.text = product.description
            Glide.with(binding.imageProduct.context)
                .load("https://apolisrises.co.in/myshop/images/${product.productImageUrl}")
                .into(binding.imageProduct)

            val rating = product.averageRating.toFloatOrNull()?: 0f
            binding.ratingBar.rating = rating
        }
    }
}
