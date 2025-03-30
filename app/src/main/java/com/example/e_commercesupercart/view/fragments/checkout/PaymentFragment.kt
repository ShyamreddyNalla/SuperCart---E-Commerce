package com.example.e_commercesupercart.view.fragments.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.e_commercesupercart.R
import com.example.e_commercesupercart.databinding.FragmentPaymentBinding
import com.example.e_commercesupercart.view.CheckoutActivity
import com.example.e_commercesupercart.viewmodel.CheckoutViewModel

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private var selectedPaymentOption: String? = null
    private val checkoutViewModel: CheckoutViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)

        binding.radioGroupPaymentOptions.setOnCheckedChangeListener { _, checkedId ->
            selectedPaymentOption = when (checkedId) {
                R.id.rbCashOnDelivery -> "Cash on Delivery"
                R.id.rbCreditDebit -> "Credit/Debit Card"
                R.id.rbInternetBanking -> "Internet Banking"
                R.id.rbPaypal -> "PayPal"
                else -> null
            }
        }


        binding.btnNext.setOnClickListener {
            if (selectedPaymentOption != null) {

                checkoutViewModel.selectedPaymentMethod.value = selectedPaymentOption

                (activity as? CheckoutActivity)?.moveToNextTab()

            } else {
                Toast.makeText(context, "Please select a payment option", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}
