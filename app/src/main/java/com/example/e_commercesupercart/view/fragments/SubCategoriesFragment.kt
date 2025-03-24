package com.example.e_commercesupercart.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.e_commercesupercart.databinding.FragmentSubCategoryBinding
import com.example.e_commercesupercart.model.ApiClient
import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.repository.SubCategoryRepository
import com.example.e_commercesupercart.model.subcategories.Subcategory
import com.example.e_commercesupercart.view.adaptors.SubcategoryPagerAdapter
import com.example.e_commercesupercart.viewmodel.SubCategoryViewModel
import com.example.e_commercesupercart.viewmodel.SubCategoryViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class SubcategoriesFragment : Fragment() {

    private lateinit var binding: FragmentSubCategoryBinding
    private lateinit var subcategoryViewModel: SubCategoryViewModel
    private lateinit var categoryId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubCategoryBinding.inflate(inflater, container, false)
        categoryId = arguments?.getString("categoryId") ?: ""

        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val subCategoryRepository = SubCategoryRepository(apiService)
        subcategoryViewModel = ViewModelProvider(
            this,
            SubCategoryViewModelFactory(subCategoryRepository)
        ).get(SubCategoryViewModel::class.java)

        subcategoryViewModel.getSubcategories(categoryId)

        subcategoryViewModel.subCategoriesLiveData.observe(viewLifecycleOwner) { subcategories ->
            if (subcategories.isNotEmpty()) {
                setupTabs(subcategories)
            } else {
                Toast.makeText(requireContext(), "Failed to load subcategories", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun setupTabs(subcategories: List<Subcategory>) {
        binding.viewPager.adapter = SubcategoryPagerAdapter(this, subcategories)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = subcategories[position].subcategoryName
        }.attach()
    }
}