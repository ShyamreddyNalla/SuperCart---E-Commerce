package com.example.e_commercesupercart.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import com.example.e_commercesupercart.R
import com.example.e_commercesupercart.databinding.ActivityMainBinding
import com.example.e_commercesupercart.view.fragments.CategoriesFragment
import com.example.e_commercesupercart.view.fragments.LoginFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        setContentView(binding.root)
        splashScreen.setKeepOnScreenCondition { false }

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (!isLoggedIn) {
            navigateToLogin()
        } else {

            showCategories()
        }
    }



    private fun navigateToLogin() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginFragment())
            .commit()
    }



    private fun showCategories() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CategoriesFragment())
            .commit()
    }
}
