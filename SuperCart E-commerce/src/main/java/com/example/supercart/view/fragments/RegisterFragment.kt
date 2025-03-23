package com.example.supercart.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.supercart.R
import com.example.supercart.databinding.FragmentRegisterBinding
import com.example.supercart.model.RegisterRequest
import com.example.supercart.model.RegisterResponse
import com.example.supercart.model.clients.ApiClient
import com.example.supercart.model.clients.ApiService
import com.example.supercart.model.repository.RegisterRepository
import com.example.supercart.viewmodel.RegisterViewModel
import com.example.supercart.viewmodel.RegisterViewModelFactory

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val registerRepository = RegisterRepository(ApiClient.retrofit.create(ApiService::class.java))
        registerViewModel = ViewModelProvider(this, RegisterViewModelFactory(registerRepository)).get(RegisterViewModel::class.java)

        registerViewModel.registerResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (it.status == 0) {
                    Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                } else {
                    Toast.makeText(requireContext(), "Registration Failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        registerViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerButton.setOnClickListener {
            val fullName = binding.fullName.text.toString()
            val mobileNo = binding.mobileNo.text.toString()
            val emailId = binding.emailId.text.toString()
            val registerPassword = binding.password.text.toString()

            if (fullName.isEmpty() || mobileNo.isEmpty() || emailId.isEmpty() || registerPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var isValid = true

            if (!ValidationUtils.validateName(fullName)) {
                binding.fullName.error = "Enter a valid name"
                isValid = false
            }

            if (!ValidationUtils.validateMobileNumber(mobileNo)) {
                binding.mobileNo.error = "Enter a valid 10-digit mobile number"
                isValid = false
            }

            if (!ValidationUtils.validateEmail(emailId)) {
                binding.emailId.error = "Enter a valid email"
                isValid = false
            }

            if (!ValidationUtils.validatePassword(registerPassword)) {
                binding.password.error = "Password must have 8+ chars, 1 uppercase, 1 number, 1 symbol"
                isValid = false
            }

            if (isValid) {
                registerViewModel.registerUser(fullName, mobileNo, emailId, registerPassword)
            }
        }

        binding.haveAccount.setOnClickListener {
            navigateToLogin()
        }

        return binding.root
    }

    private fun navigateToLogin() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginFragment())
            .addToBackStack("register")
            .commit()
    }
}
