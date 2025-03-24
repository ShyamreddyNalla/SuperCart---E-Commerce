package com.example.e_commercesupercart.view.adaptors

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commercesupercart.databinding.ProductsListCategoriesBinding
import com.example.e_commercesupercart.model.Category


class CategoryViewHolder(private val binding: ProductsListCategoriesBinding,
                         private val onCategoryClick: (String) -> Unit ): RecyclerView.ViewHolder(binding.root) {
    fun bind(category: Category){
        binding.categoryName.text = category.categoryName

        val imageUrl = "https://apolisrises.co.in/myshop/images/${category.categoryImageUrl}"

        Glide.with(binding.categoryImage.context)
            .load(imageUrl)
            .into(binding.categoryImage)

        binding.root.setOnClickListener {
            onCategoryClick(category.categoryId)
        }
    }
}