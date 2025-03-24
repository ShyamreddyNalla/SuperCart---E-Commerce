package com.example.e_commercesupercart.view.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercesupercart.databinding.ProductsListCategoriesBinding
import com.example.e_commercesupercart.model.Category

class CategoryAdapter(private val categories: List<Category>,
                      private val onCategoryClick: (String) -> Unit ): RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ProductsListCategoriesBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false)
        return CategoryViewHolder(binding, onCategoryClick)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category= categories[position]
        holder.bind(category)
    }
}

