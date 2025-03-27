package com.example.e_commercesupercart.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercesupercart.databinding.FragmentProductDetailsBinding
import com.example.e_commercesupercart.model.ApiClient
import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.product_details.Product
import com.example.e_commercesupercart.model.repository.ProductDetailRepository
import com.example.e_commercesupercart.model.roomdb.CartDao
import com.example.e_commercesupercart.model.roomdb.CartDatabase
import com.example.e_commercesupercart.model.roomdb.CartItem
import com.example.e_commercesupercart.model.roomdb.CartRepository
import com.example.e_commercesupercart.view.adaptors.ProductImageAdapter
import com.example.e_commercesupercart.view.adaptors.ReviewAdapter
import com.example.e_commercesupercart.view.adaptors.SpecificationAdapter
import com.example.e_commercesupercart.viewmodel.CartViewModel
import com.example.e_commercesupercart.viewmodel.CartViewModelFactory
import com.example.e_commercesupercart.viewmodel.ProductDetailsViewModel
import com.example.e_commercesupercart.viewmodel.ProductDetailsViewModelFactory


class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var productDetailsViewModel: ProductDetailsViewModel
    private lateinit var imageAdapter: ProductImageAdapter
    private lateinit var specAdapter: SpecificationAdapter
    private lateinit var cartViewModel: CartViewModel
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var cartRepository: CartRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        val productId = arguments?.getString("product_id") ?: return binding.root

        val repository = ProductDetailRepository(ApiClient.retrofit.create(ApiService::class.java))
        productDetailsViewModel = ViewModelProvider(this, ProductDetailsViewModelFactory(repository))[ProductDetailsViewModel::class.java]

        val cartDao = CartDatabase.getDatabase(requireContext()).cartDao()
        cartRepository = CartRepository(cartDao)
        cartViewModel = ViewModelProvider(this,CartViewModelFactory(cartRepository))[CartViewModel::class.java]
        productDetailsViewModel.fetchProductDetails(productId)
        imageAdapter = ProductImageAdapter()
        reviewAdapter = ReviewAdapter()

        binding.viewPagerImages.adapter = imageAdapter


        binding.recyclerReviews.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerReviews.adapter = reviewAdapter

        productDetailsViewModel.productDetails.observe(viewLifecycleOwner) { product ->
            if (product != null) {
                specAdapter = SpecificationAdapter(product.specifications)
            }
            binding.recyclerSpecifications.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerSpecifications.adapter = specAdapter
            product?.let { updateUI(it) }


        }

        productDetailsViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        }

        productDetailsViewModel.fetchProductDetails(productId)

        binding.buttonAddToCart.setOnClickListener {
            val product = productDetailsViewModel.productDetails.value
            product?.let {
                val cartItem = CartItem(
                    productId = it.productId.toInt(),
                    productName = it.productName,
                    productPrice = it.price,
                    productImage = (it.productImageUrl.firstOrNull() ?: "").toString(),
                    quantity = 1
                )
                cartViewModel.addItem(cartItem)
                Toast.makeText(requireContext(), "Added to cart", Toast.LENGTH_SHORT).show()
                binding.buttonAddToCart.visibility = View.GONE
                binding.quantityLayout.visibility = View.VISIBLE
                binding.textQuantity.text = "1"
            }
        }

        binding.buttonPlus.setOnClickListener {
            val product = productDetailsViewModel.productDetails.value
            product?.let {
                val updatedQuantity = binding.textQuantity.text.toString().toInt() + 1
                binding.textQuantity.text = updatedQuantity.toString()

                val updatedItem = CartItem(
                    productId = it.productId.toInt(),
                    productName = it.productName,
                    productPrice = it.price,
                    productImage = (it.productImageUrl.firstOrNull() ?: "").toString(),
                    quantity = updatedQuantity
                )
                cartViewModel.addItem(updatedItem)
            }
        }

        binding.buttonMinus.setOnClickListener {
            val product = productDetailsViewModel.productDetails.value
            product?.let {
                val updatedQuantity = (binding.textQuantity.text.toString().toInt() - 1).coerceAtLeast(0)
                binding.textQuantity.text = updatedQuantity.toString()

                val updatedItem = CartItem(
                    productId = it.productId.toInt(),
                    productName = it.productName,
                    productPrice = it.price,
                    productImage = (it.productImageUrl.firstOrNull() ?: "").toString(),
                    quantity = updatedQuantity
                )

                if (updatedQuantity > 0) {
                    cartViewModel.addItem(updatedItem)
                } else {
                    cartViewModel.removeItem(updatedItem)
                    binding.buttonAddToCart.visibility = View.VISIBLE
                    binding.quantityLayout.visibility = View.GONE
                }
            }
        }

        return binding.root
    }

    private fun updateUI(product: Product) {
        binding.textProductName.text = product.productName
        binding.textProductPrice.text = "₹${product.price}"
        binding.textProductDescription.text = product.description

        imageAdapter.submitList(product.images)
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
