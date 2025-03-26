package com.example.e_commercesupercart.view.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commercesupercart.databinding.ItemProductImageBinding
import com.example.e_commercesupercart.model.product_details.Image

class ProductImageAdapter : RecyclerView.Adapter<ProductImageAdapter.ImageViewHolder>() {

    private val images = mutableListOf<Image>()

    fun submitList(newImages: List<Image>) {
        images.clear()
        images.addAll(newImages)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemProductImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = "https://apolisrises.co.in/myshop/images/" + images[position].image
        Glide.with(holder.binding.imageProduct.context)
            .load(imageUrl)
            .into(holder.binding.imageProduct)
    }

    override fun getItemCount(): Int = images.size

    class ImageViewHolder(val binding: ItemProductImageBinding) : RecyclerView.ViewHolder(binding.root)
}
