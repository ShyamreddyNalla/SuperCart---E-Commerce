package com.example.e_commercesupercart.view.adaptors

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.e_commercesupercart.model.subcategories.Subcategory
import com.example.e_commercesupercart.view.fragments.SubcategoryProductsFragment


class SubcategoryPagerAdapter(
    fragment: Fragment,
    private val subcategories: List<Subcategory>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = subcategories.size

    override fun createFragment(position: Int): Fragment {
        val subcategoryFragment = SubcategoryProductsFragment()
        subcategoryFragment.arguments = Bundle().apply {
            putString("subcategoryId", subcategories[position].subcategoryId)
            putString("subcategoryName", subcategories[position].subcategoryName)
        }
        return subcategoryFragment
    }
}