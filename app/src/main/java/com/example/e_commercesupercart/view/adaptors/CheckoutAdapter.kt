package com.example.e_commercesupercart.view.adaptors

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.e_commercesupercart.view.fragments.checkout.CartItemsFragment
import com.example.e_commercesupercart.view.fragments.checkout.DeliveryFragment
import com.example.e_commercesupercart.view.fragments.checkout.PaymentFragment
import com.example.e_commercesupercart.view.fragments.checkout.SummaryFragment

class CheckoutAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CartItemsFragment()
            1 -> DeliveryFragment()
            2 -> PaymentFragment()
            3 -> SummaryFragment()
            else -> throw IllegalStateException("Invalid position")
        }
    }
}
