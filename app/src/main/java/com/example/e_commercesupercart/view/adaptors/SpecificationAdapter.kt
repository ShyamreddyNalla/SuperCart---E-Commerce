package com.example.e_commercesupercart.view.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercesupercart.databinding.ItemSpecificationBinding
import com.example.e_commercesupercart.model.product_details.Specification

class SpecificationAdapter( private val specifications: List<Specification>
): RecyclerView.Adapter<SpecificationAdapter.SpecViewHolder>() {


    fun submitList(newSpecs: List<Specification>) {
        //specifications.clear()
      //  specifications.addAll(newSpecs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecViewHolder {
        val binding = ItemSpecificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecViewHolder, position: Int) {
        val spec = specifications[position]
        holder.binding.textSpecTitle.text = spec.title
        holder.binding.textSpecValue.text = spec.specification

    }

    override fun getItemCount(): Int = specifications.size

    class SpecViewHolder(val binding: ItemSpecificationBinding) : RecyclerView.ViewHolder(binding.root)
}
