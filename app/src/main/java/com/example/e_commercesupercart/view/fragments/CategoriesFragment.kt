package com.example.e_commercesupercart.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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
import com.example.e_commercesupercart.viewmodel.CategoryViewModelFactory

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
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
                    // Navigate to SubcategoriesFragment
                    navigateToSubcategoriesFragment(categoryId)
                }
            } else {
                Toast.makeText(requireContext(), "Failed to load categories", Toast.LENGTH_SHORT).show()
            }
        }

        categoryViewModel.getCategories()
        setupNavigationDrawer()

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

        val sharedPreferences = requireContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("userName", "User Name")
        val userEmail = sharedPreferences.getString("userEmail", "user@example.com")
        val userPhone = sharedPreferences.getString("userPhone", "1234567890")

        navHeadBinding = NavHeadBinding.bind(binding.navigationView.getHeaderView(0))

        navHeadBinding.userName.text = userName
        navHeadBinding.userEmail.text = userEmail
        navHeadBinding.userPhone.text = userPhone
    }
}
