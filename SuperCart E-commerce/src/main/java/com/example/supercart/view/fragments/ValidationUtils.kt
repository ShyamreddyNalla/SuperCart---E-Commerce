package com.example.supercart.view.fragments

object ValidationUtils {

    fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        return password.matches(Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#\$%^&+=!]).{8,}$"))
    }

    fun validateName(name: String): Boolean {
        return name.matches("^[a-zA-Z\\s]+$".toRegex())
    }

    fun validateMobileNumber(mobileNo: String): Boolean {
        return mobileNo.matches("^[0-9]{10}$".toRegex())
    }
}