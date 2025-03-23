package com.example.supercart.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.supercart.view.MainActivity
import com.example.supercart.R
import com.example.supercart.databinding.FragmentLoginBinding
import com.example.supercart.model.LoginRequest
import com.example.supercart.model.LoginResponse
import com.example.supercart.model.clients.ApiClient
import com.example.supercart.model.clients.ApiService
import com.example.supercart.model.repository.LoginRepository
import com.example.supercart.viewmodel.LoginViewModel
import com.example.supercart.viewmodel.LoginViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        val repository = LoginRepository()
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(repository)).get(LoginViewModel::class.java)

        binding.emailEdText.doAfterTextChanged { binding.emailEdText.error = null }
        binding.edTextPassword.doAfterTextChanged { binding.edTextPassword.error = null }

        binding.loginButton.setOnClickListener { validateAndLogin() }
        binding.textNoAccount.setOnClickListener { navigateToRegister() }

        loginViewModel.loginResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (it.status == 0) {
                    val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", AppCompatActivity.MODE_PRIVATE)
                    sharedPreferences.edit()
                        .putBoolean("isLoggedIn", true)
                        .apply()

                    Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
                    navigateToMainActivity()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Login Failed, please try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        return binding.root
    }

    private fun validateAndLogin() {
        val email = binding.emailEdText.text.toString().trim()
        val password = binding.edTextPassword.text.toString().trim()

        binding.emailEdText.error = null
        binding.edTextPassword.error = null

        var isValid = true

        if (email.isEmpty()) {
            binding.emailEdText.error = "Please enter email"
            isValid = false
        } else if (!ValidationUtils.validateEmail(email)) {
            binding.emailEdText.error = "Enter a valid email"
            isValid = false
        }

        if (password.isEmpty()) {
            binding.edTextPassword.error = "Please enter password"
            isValid = false
        } else if (!ValidationUtils.validatePassword(password)) {
            binding.edTextPassword.error = "Password must have 8+ chars, 1 uppercase, 1 number, 1 symbol"
            isValid = false
        }

        if (isValid) {
            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        loginViewModel.loginUser(email, password)
    }

    private fun navigateToRegister(){
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, RegisterFragment())
            .addToBackStack("loginFragment")
            .commit()
    }

    private fun navigateToMainActivity() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CategoriesFragment())
            .commit()
    }
}