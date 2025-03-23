package com.example.supercart.view

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.supercart.databinding.ProductsListCategoriesBinding
import com.example.supercart.model.Category

class CategoryViewHolder(private val binding: ProductsListCategoriesBinding):RecyclerView.ViewHolder(binding.root) {
     fun bind(category: Category){
         binding.categoryName.text = category.categoryName

        val imageUrl = "https://apolisrises.co.in/myshop/images/${category.categoryImageUrl}"

         Glide.with(binding.categoryImage.context)
             .load(imageUrl)
             .into(binding.categoryImage)

     }
}