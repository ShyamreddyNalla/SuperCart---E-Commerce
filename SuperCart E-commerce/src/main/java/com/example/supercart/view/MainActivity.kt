package com.example.supercart.view
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import com.example.supercart.R
import com.example.supercart.databinding.ActivityMainBinding
import com.example.supercart.view.fragments.CategoriesFragment
import com.example.supercart.view.fragments.LoginFragment
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        setContentView(binding.root)
        splashScreen.setKeepOnScreenCondition { false }

        setUpNavigationDrawer()

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (!isLoggedIn) {
            navigateToLogin()
        } else {

            showCategories()
        }
    }


    private fun setUpNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout,
            R.string.open, R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.nav_home -> showCategories()
                R.id.nav_logout -> logout()
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()

        navigateToLogin()
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
