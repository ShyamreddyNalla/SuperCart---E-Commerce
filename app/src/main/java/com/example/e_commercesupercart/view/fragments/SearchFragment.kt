package com.example.e_commercesupercart.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercesupercart.databinding.FragmentSearchBinding
import com.example.e_commercesupercart.model.ApiClient
import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.repository.SearchRepository
import com.example.e_commercesupercart.view.adaptors.ProductAdapter
import com.example.e_commercesupercart.viewmodel.SearchViewModel
import com.example.e_commercesupercart.viewmodel.SearchViewModelFactory

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val searchRepository = SearchRepository(apiService)
        searchViewModel = ViewModelProvider(
            this,
            SearchViewModelFactory(searchRepository)
        )[SearchViewModel::class.java]

        setupRecyclerView()
        observeViewModel()
        setUpSearchView()

        return binding.root
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(emptyList())
        binding.recyclerViewSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }

    private fun setUpSearchView() {
        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchViewModel.searchProducts(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    productAdapter.updateProducts(emptyList())
                    binding.recyclerViewSearchResults.visibility = View.GONE
                } else {
                    searchViewModel.searchProducts(newText)
                }
                return true
            }
        })
    }

    private fun observeViewModel() {
        searchViewModel.searchResult.observe(viewLifecycleOwner) { response ->
            if (response != null && response.products.isNotEmpty()) {
                binding.recyclerViewSearchResults.visibility = View.VISIBLE
                productAdapter.updateProducts(response.products)
            } else {
                binding.recyclerViewSearchResults.visibility = View.GONE
                Toast.makeText(requireContext(), "No products found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
