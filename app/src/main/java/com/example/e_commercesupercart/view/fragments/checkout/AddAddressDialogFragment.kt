package com.example.e_commercesupercart.view.fragments.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.e_commercesupercart.databinding.DialogAddFragmentBinding

class AddAddressDialogFragment(private val onAddressAdded: (String, String) -> Unit) : DialogFragment() {

    private lateinit var binding: DialogAddFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAddFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveAddress.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val address = binding.etAddress.text.toString()

            if (title.isNotEmpty() && address.isNotEmpty()) {
                onAddressAdded(title, address)  // Pass the new address to the parent fragment
                dismiss()
            } else {
                Toast.makeText(context, "Please fill in both fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
