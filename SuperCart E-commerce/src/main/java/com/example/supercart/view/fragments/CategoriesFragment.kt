package com.example.supercart.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.supercart.view.CategoryAdapter
import com.example.supercart.model.clients.ApiClient
import com.example.supercart.model.clients.ApiService
import com.example.supercart.databinding.FragmentCategoryBinding
import com.example.supercart.model.CategoryResponse
import com.example.supercart.model.repository.CategoryRepository
import com.example.supercart.viewmodel.CategoryViewModel
import com.example.supercart.viewmodel.CategoryViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesFragment: Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater,container,false)
        binding.recyclerViewCategories.layoutManager = GridLayoutManager(requireContext(),2)

        val  apiService = ApiClient.retrofit.create(ApiService::class.java)
        val categoryRepository = CategoryRepository(apiService)
        categoryViewModel = ViewModelProvider(this, CategoryViewModelFactory(categoryRepository))
            .get(CategoryViewModel::class.java)

        categoryViewModel.categoriesLiveData.observe(viewLifecycleOwner, {response ->
            if (response != null && response.status == 0){
                binding.recyclerViewCategories.adapter = CategoryAdapter(response.categories)
        }else{
            Toast.makeText(requireContext(),"Failed to load categories", Toast.LENGTH_SHORT ).show()
        }
        })
       categoryViewModel.getCategories()
        return binding.root
    }


}