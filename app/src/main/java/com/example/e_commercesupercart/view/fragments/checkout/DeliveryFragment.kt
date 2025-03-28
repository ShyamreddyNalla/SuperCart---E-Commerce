package com.example.e_commercesupercart.view.fragments.checkout

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercesupercart.databinding.FragmentDeliveryBinding
import com.example.e_commercesupercart.view.adaptors.AddressAdapter
import com.example.e_commercesupercart.viewmodel.AddressViewModel
import com.example.e_commercesupercart.model.repository.AddressRepository
import com.example.e_commercesupercart.model.ApiClient
import com.example.e_commercesupercart.viewmodel.AddressViewModelFactory
class DeliveryFragment : Fragment() {

    private lateinit var binding: FragmentDeliveryBinding
    private lateinit var addressViewModel: AddressViewModel
    private lateinit var addressAdapter: AddressAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addressRepository = AddressRepository(ApiClient.apiService)
        val viewModelFactory = AddressViewModelFactory(addressRepository)
        addressViewModel = ViewModelProvider(this, viewModelFactory).get(AddressViewModel::class.java)

        // Initialize RecyclerView Adapter
        addressAdapter = AddressAdapter()
        binding.rvSavedAddresses.layoutManager = LinearLayoutManager(context)
        binding.rvSavedAddresses.adapter = addressAdapter

        // Observe address list from the server
        addressViewModel.addresses.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                addressAdapter.submitList(it)
            }
        })

        // Observe status of adding address
        addressViewModel.addAddressStatus.observe(viewLifecycleOwner, Observer { status ->
            Toast.makeText(context, status, Toast.LENGTH_SHORT).show()
        })

        // Open the Add Address dialog
        binding.btnAddAddress.setOnClickListener {
            showAddAddressDialog()
        }

        // Get the user's addresses (userId should be dynamic, not hardcoded)
        val userId = 2 // Replace this with the actual logged-in user ID
        addressViewModel.getAddresses(userId)
    }

    private fun showAddAddressDialog() {
        val dialog = AddAddressDialogFragment { title, address ->
            val userId = 2 // Replace this with the actual logged-in user ID
            addressViewModel.addAddress(userId, title, address)
        }
        dialog.show(childFragmentManager, "AddAddressDialog")
    }
}