package com.example.e_commercesupercart.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercesupercart.R
import com.example.e_commercesupercart.databinding.FragmentSubcategoryProductsBinding
import com.example.e_commercesupercart.model.ApiClient
import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.repository.ProductRepository
import com.example.e_commercesupercart.view.adaptors.ProductAdapter
import com.example.e_commercesupercart.viewmodel.ProductViewModel
import com.example.e_commercesupercart.viewmodel.ProductViewModelFactory


class SubcategoryProductsFragment : Fragment() {

    private lateinit var binding: FragmentSubcategoryProductsBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var subcategoryId: String
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubcategoryProductsBinding.inflate(inflater, container, false)

        subcategoryId = arguments?.getString("subcategoryId") ?: ""
        productAdapter = ProductAdapter(listOf()){ productId ->
            openProductDetails(productId)
        }
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewProducts.adapter = productAdapter

        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val productRepository = ProductRepository(apiService)
        productViewModel = ViewModelProvider(this, ProductViewModelFactory(productRepository)
            ).get(ProductViewModel::class.java)

        observeProducts()

        return binding.root
    }

    private fun observeProducts() {
        productViewModel.productLiveData.observe(viewLifecycleOwner) { products ->
            if (products != null && products.isNotEmpty()) {
                productAdapter.updateProducts(products)
            } else {
                Toast.makeText(requireContext(), "No products found", Toast.LENGTH_SHORT).show()
            }
        }

        productViewModel.errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        productViewModel.getProducts(subcategoryId)
    }
    private fun openProductDetails(productId: String) {
        val fragment = ProductDetailsFragment().apply {
            arguments = Bundle().apply {
                putString("product_id", productId)
            }
        }
           requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
