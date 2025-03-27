package com.example.e_commercesupercart.model.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(cartItem: CartItem)

    @Query("SELECT * FROM cart_items")
    fun getAllItems(): LiveData<List<CartItem>>

    @Query("SELECT * FROM cart_items WHERE productId = :id")
    fun getItemById(id: String): CartItem?

    @Update
    fun updateItem(cartItem: CartItem)

    @Delete
    fun deleteItem(cartItem: CartItem)

    @Query("DELETE FROM cart_items WHERE productId = :id")
    fun deleteItemById(id: String)

    @Query("DELETE FROM cart_items")
    fun clearCart()
}
