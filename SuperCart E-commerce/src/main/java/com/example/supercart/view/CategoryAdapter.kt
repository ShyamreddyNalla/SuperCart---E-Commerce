package com.example.supercart.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supercart.databinding.ProductsListCategoriesBinding
import com.example.supercart.model.Category

class CategoryAdapter(private val categories: List<Category>):RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ProductsListCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent,
          false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category= categories[position]
       holder.bind(category)
        }
    }

