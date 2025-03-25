package com.example.e_commercesupercart.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.e_commercesupercart.R
import com.example.e_commercesupercart.databinding.FragmentRegisterBinding
import com.example.e_commercesupercart.model.ApiClient
import com.example.e_commercesupercart.model.ApiService
import com.example.e_commercesupercart.model.repository.RegisterRepository
import com.example.e_commercesupercart.viewmodel.RegisterViewModel
import com.example.e_commercesupercart.viewmodel.factories.RegisterViewModelFactory

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val registerRepository = RegisterRepository(ApiClient.retrofit.create(ApiService::class.java))
        registerViewModel = ViewModelProvider(this, RegisterViewModelFactory(registerRepository))[RegisterViewModel::class.java]

        registerViewModel.registerResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (it.status == 0 ){
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
            val fullName = binding.fullName.text.toString().trim()
            val mobileNo = binding.mobileNo.text.toString().trim()
            val emailId = binding.emailId.text.toString().trim()
            val registerPassword = binding.password.text.toString().trim()

            if (validateInputs(fullName, mobileNo, emailId, registerPassword)) {
                registerViewModel.registerUser(fullName, mobileNo, emailId, registerPassword)
            }
        }

        binding.haveAccount.setOnClickListener {
            navigateToLogin()
        }

        return binding.root
    }

    private fun validateInputs(fullName: String, mobileNo: String, emailId: String, password: String): Boolean {
        var isValid = true

        if (fullName.isEmpty()) {
            binding.fullName.error = "Enter a valid name"
            isValid = false
        }

        if (mobileNo.length != 10 || !mobileNo.matches(Regex("\\d{10}"))) {
            binding.mobileNo.error = "Enter a valid 10-digit mobile number"
            isValid = false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            binding.emailId.error = "Enter a valid email"
            isValid = false
        }

        if (!isValidPassword(password)) {
            binding.password.error = "Password must have 8+ chars, 1 uppercase, 1 number, 1 symbol"
            isValid = false
        }

        return isValid
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = Regex("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}\$")
        return passwordPattern.matches(password)
    }

    private fun navigateToLogin() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginFragment())
            .addToBackStack("register")
            .commit()
    }
    private fun saveUserDetails(fullName: String,mobileNo: String,emailId: String){
        val sharedPreferences = requireContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("UserName", fullName)
        editor.putString("UserEmail", emailId)
        editor.putString("UserPhone", mobileNo)
        val isSaved = editor.commit()
        if(isSaved){
            Log.d("SaveUserDetails", "Saved $fullName,$emailId, $mobileNo")
        }else{
            Log.d("SaveUserDetails", "Failed to save user details")
        }
     editor.apply()
    }
}
