package com.example.e_commercesupercart.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.e_commercesupercart.R
import com.example.e_commercesupercart.databinding.ActivityCheckOutBinding
import com.example.e_commercesupercart.view.adaptors.CheckoutAdapter
import com.example.e_commercesupercart.view.fragments.CartFragment
import com.example.e_commercesupercart.view.fragments.CategoriesFragment
import com.example.e_commercesupercart.view.fragments.LoginFragment
import com.example.e_commercesupercart.view.fragments.OrderDetailsFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.example.e_commercesupercart.viewmodel.CheckoutViewModel

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckOutBinding
    private val checkoutViewModel: CheckoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationIcon(R.drawable.baseline_menu_24)
        binding.drawerLayout.closeDrawer(GravityCompat.START)

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
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

        setupDrawerMenu()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setupDrawerMenu() {
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                  supportFragmentManager.beginTransaction()
                      .replace(R.id.fragment_container, CategoriesFragment())
                      .addToBackStack(null)
                      .commit()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_cart -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, CartFragment())
                        .addToBackStack(null)
                        .commit()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_orders -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, OrderDetailsFragment())
                        .addToBackStack(null)
                        .commit()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_logout -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, LoginFragment())
                        .addToBackStack(null)
                        .commit()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }
    }
    fun moveToNextTab() {
        val nextTab = binding.viewPager.currentItem + 1
        if (nextTab < (binding.viewPager.adapter?.itemCount ?: 0)) {

            binding.viewPager.currentItem = nextTab
        }
    }
}
