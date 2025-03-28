package com.example.e_commercesupercart.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.example.e_commercesupercart.R
import com.example.e_commercesupercart.databinding.ActivityCheckOutBinding
import com.example.e_commercesupercart.view.adaptors.CheckoutAdapter
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckOutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        val adapter = CheckoutAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Cart Items"
                1 -> "Delivery"
                2 -> "Payment"
                3 -> "Summary"
                else -> ""
            }
        }.attach()
    }
    fun moveToNextTab() {
        val nextTab = binding.viewPager.currentItem + 1
        if (nextTab < (binding.viewPager.adapter?.itemCount ?: 0)) {
            binding.viewPager.currentItem = nextTab
        }
    }

}
