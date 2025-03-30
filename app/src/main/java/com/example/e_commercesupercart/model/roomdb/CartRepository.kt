package com.example.e_commercesupercart.model.roomdb

import androidx.lifecycle.LiveData

class CartRepository(private val cartDao: CartDao) {

    val allCartItems: LiveData<List<CartItem>> = cartDao.getAllItems()

    fun addItem(cartItem: CartItem) {
        val existingItem = cartDao.getItemById(cartItem.productId.toString())
        if (existingItem == null) {
            cartDao.insertItem(cartItem)
        } else {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            cartDao.updateItem(updatedItem)
        }
    }

    fun decreaseItemQuantity(cartItem: CartItem) {
        if (cartItem.quantity > 1) {
            val updatedItem = cartItem.copy(quantity = cartItem.quantity - 1)
            cartDao.updateItem(updatedItem)
        } else {
            cartDao.deleteItem(cartItem)
        }
    }

    fun removeItem(cartItem: CartItem) {
        cartDao.deleteItem(cartItem)
    }
    fun getProductCount(productId: Int): LiveData<Int> {
        return cartDao.getProductCount(productId)
    }


}