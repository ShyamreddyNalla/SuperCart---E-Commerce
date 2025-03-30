package com.example.e_commercesupercart.view.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercesupercart.databinding.ItemAddressBinding
import com.example.e_commercesupercart.model.Address

class AddressAdapter(
    private val onAddressSelected: (Address) -> Unit
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    private var addressList = listOf<Address>()

    fun submitList(list: List<Address>) {
        addressList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val address = addressList[position]
        holder.bind(address)
    }

    override fun getItemCount(): Int = addressList.size

    inner class AddressViewHolder(private val binding: ItemAddressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(address: Address) {
            binding.tvTitle.text = address.title
            binding.tvAddress.text = address.address

            binding.root.setOnClickListener {
                onAddressSelected(address)
            }
        }
    }
}
