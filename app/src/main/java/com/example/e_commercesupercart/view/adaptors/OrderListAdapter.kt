package com.example.e_commercesupercart.view.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercesupercart.R
import com.example.e_commercesupercart.databinding.ItemOrderListBinding
import com.example.e_commercesupercart.model.orders.OrderDetails
class OrderListAdapter(var orders: List<OrderDetails?>, ) :
    RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(order: OrderDetails) {
            val binding = ItemOrderListBinding.bind(itemView)
            binding.addressTitle.text = order.addressTitle
            binding.address.text = order.address
            binding.billAmount.text = "₹${order.billAmount}"
            binding.paymentMethod.text = order.paymentMethod
            binding.orderStatus.text = order.orderStatus
          //  itemView.setOnClickListener { onItemClick(order) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_list, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        orders[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}
