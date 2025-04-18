package com.example.e_commercesupercart.view.fragments.checkout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercesupercart.databinding.FragmentDeliveryBinding
import com.example.e_commercesupercart.view.adaptors.AddressAdapter
import com.example.e_commercesupercart.viewmodel.AddressViewModel
import com.example.e_commercesupercart.viewmodel.CheckoutViewModel
import com.example.e_commercesupercart.model.repository.AddressRepository
import com.example.e_commercesupercart.model.ApiClient
import com.example.e_commercesupercart.view.CheckoutActivity
import com.example.e_commercesupercart.viewmodel.AddressViewModelFactory
class DeliveryFragment : Fragment() {

    private lateinit var binding: FragmentDeliveryBinding
    private lateinit var addressViewModel: AddressViewModel
    private lateinit var addressAdapter: AddressAdapter
    private val checkoutViewModel: CheckoutViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryBinding.inflate(inflater, container, false)

        binding.btnNext.setOnClickListener {
            val selectedAddress = checkoutViewModel.selectedDeliveryAddress.value
            if (selectedAddress.isNullOrEmpty()) {
                Toast.makeText(context, "Please select an address", Toast.LENGTH_SHORT).show()
            } else {
                (activity as? CheckoutActivity)?.moveToNextTab()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addressRepository = AddressRepository(ApiClient.apiService)
        val viewModelFactory = AddressViewModelFactory(addressRepository)
        addressViewModel = ViewModelProvider(this, viewModelFactory)[AddressViewModel::class.java]

        addressAdapter = AddressAdapter { selectedAddress ->
            checkoutViewModel.selectedDeliveryAddressTitle.value = selectedAddress.title
            checkoutViewModel.selectedDeliveryAddress.value = selectedAddress.address
            Log.d("DeliveryFragment", "Selected Address: ${selectedAddress.title}")
            Toast.makeText(context, "Address selected: ${selectedAddress.title}", Toast.LENGTH_SHORT).show()
            (activity as? CheckoutActivity)?.moveToNextTab()
        }

        binding.rvSavedAddresses.layoutManager = LinearLayoutManager(context)
        binding.rvSavedAddresses.adapter = addressAdapter

        addressViewModel.addresses.observe(viewLifecycleOwner, { addresses ->
            if (addresses != null) {
                addressAdapter.submitList(addresses)
            }
        })

        addressViewModel.addAddressStatus.observe(viewLifecycleOwner, { status ->
            Toast.makeText(context, status, Toast.LENGTH_SHORT).show()
        })

        val userId = 1
        addressViewModel.getAddresses(userId)

        binding.btnAddAddress.setOnClickListener {
            showAddAddressDialog()
        }
    }

    private fun showAddAddressDialog() {
        val dialog = AddAddressDialogFragment { title, address ->
            val userId = 1
            addressViewModel.addAddress(userId, title, address)
        }
        dialog.show(childFragmentManager, "AddAddressDialog")
    }
}
