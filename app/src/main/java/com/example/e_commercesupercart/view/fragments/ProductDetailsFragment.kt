package com.example.e_commercesupercart.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercesupercart.R
import com.example.e_commercesupercart.databinding.FragmentProductDetailsBinding
import com.example.e_commercesupercart.model.ApiClient
import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.product_details.Product
import com.example.e_commercesupercart.model.repository.ProductDetailRepository
import com.example.e_commercesupercart.view.adaptors.ProductImageAdapter
import com.example.e_commercesupercart.view.adaptors.ReviewAdapter
import com.example.e_commercesupercart.view.adaptors.SpecificationAdapter
import com.example.e_commercesupercart.viewmodel.ProductDetailsViewModel
import com.example.e_commercesupercart.viewmodel.ProductDetailsViewModelFactory


class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var productDetailsViewModel: ProductDetailsViewModel
    private lateinit var imageAdapter: ProductImageAdapter
    private lateinit var specAdapter: SpecificationAdapter
    private lateinit var reviewAdapter: ReviewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        val productId = arguments?.getString("product_id") ?: return binding.root

        val repository = ProductDetailRepository(ApiClient.retrofit.create(ApiService::class.java))
        productDetailsViewModel = ViewModelProvider(this, ProductDetailsViewModelFactory(repository))[ProductDetailsViewModel::class.java]


        imageAdapter = ProductImageAdapter()
        specAdapter = SpecificationAdapter()
        reviewAdapter = ReviewAdapter()

        binding.viewPagerImages.adapter = imageAdapter

        binding.recyclerSpecifications.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSpecifications.adapter = specAdapter

        binding.recyclerReviews.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerReviews.adapter = reviewAdapter

        productDetailsViewModel.productDetails.observe(viewLifecycleOwner) { product ->
            product?.let { updateUI(it) }
        }

        productDetailsViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        }

        productDetailsViewModel.fetchProductDetails(productId)

        return binding.root
    }

    private fun updateUI(product: Product) {
        binding.textProductName.text = product.productName
        binding.textProductPrice.text = "₹${product.price}"
        binding.textProductDescription.text = product.description

        imageAdapter.submitList(product.images)
        specAdapter.submitList(product.specifications)
        reviewAdapter.submitList(product.reviews)
    }
    companion object {
        fun newInstance(productId: String): ProductDetailsFragment {
            val fragment = ProductDetailsFragment()
            val args = Bundle()
            args.putString("product_id", productId)
            fragment.arguments = args
            return fragment
        }
    }
}
