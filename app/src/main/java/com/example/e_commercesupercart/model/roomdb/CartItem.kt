package com.example.e_commercesupercart.model.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val productId: Int,

    val productName: String,
    val productPrice: String,
    val productImage: String,
    val quantity: Int
)
