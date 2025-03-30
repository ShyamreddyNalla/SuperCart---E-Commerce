package com.example.e_commercesupercart.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_commercesupercart.R
import com.example.e_commercesupercart.databinding.FragmentCategoryBinding
import com.example.e_commercesupercart.databinding.NavHeadBinding
import com.example.e_commercesupercart.model.ApiClient
import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.repository.CategoryRepository
import com.example.e_commercesupercart.view.adaptors.CategoryAdapter
import com.example.e_commercesupercart.viewmodel.CategoryViewModel
import com.example.e_commercesupercart.viewmodel.factories.CategoryViewModelFactory

class CategoriesFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var navHeadBinding: NavHeadBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        binding.recyclerViewCategories.layoutManager = GridLayoutManager(requireContext(), 2)

        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val categoryRepository = CategoryRepository(apiService)
        categoryViewModel = ViewModelProvider(this, CategoryViewModelFactory(categoryRepository))
            .get(CategoryViewModel::class.java)

        categoryViewModel.categoriesLiveData.observe(viewLifecycleOwner) { categories ->
            if (categories.isNotEmpty()) {
                binding.recyclerViewCategories.adapter = CategoryAdapter(categories) { categoryId ->
                    navigateToSubcategoriesFragment(categoryId)
                }
            } else {
                Toast.makeText(requireContext(), "Failed to load categories", Toast.LENGTH_SHORT).show()
            }
        }

        categoryViewModel.getCategories()
        setupNavigationDrawer()


        binding.searchIcon.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,SearchFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }


    private fun navigateToSubcategoriesFragment(categoryId: String) {
        val subcategoryFragment = SubcategoriesFragment().apply {
            arguments = Bundle().apply {
                putString("categoryId", categoryId)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, subcategoryFragment)
            .addToBackStack(null)
            .commit()
    }
    private fun setupNavigationDrawer() {
        binding.menuIcon.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.nav_home ->{
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container,CategoriesFragment())
                        .addToBackStack(null)
                        .commit()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.nav_cart ->{
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container,CartFragment())
                        .addToBackStack(null)
                        .commit()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
               R.id.nav_orders ->{
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container,OrderDetailsFragment())
                        .addToBackStack(null)
                        .commit()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_logout ->{
                    logoutUser()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, LoginFragment())
                        .commit()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }

        val sharedPreferences = requireContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("userName", "User Name")
        val userEmail = sharedPreferences.getString("userEmail", "user@example.com")
        val userPhone = sharedPreferences.getString("userPhone", "1234567890")
        Log.d("NavHeader","UserName: $userName, UserEmail: $userEmail, UserPhone: $userPhone")
        navHeadBinding = NavHeadBinding.bind(binding.navigationView.getHeaderView(0))

        navHeadBinding.userName.text = userName
        navHeadBinding.userEmail.text = userEmail
        navHeadBinding.userPhone.text = userPhone
    }

    private fun logoutUser() {
        val sharedPreferences = requireContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        Toast.makeText(requireContext(),"Logged out successfully", Toast.LENGTH_SHORT).show()
    }


}
