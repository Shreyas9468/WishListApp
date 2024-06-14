package com.example.wishlistapp

import android.content.Context
import androidx.room.Room
import com.example.wishlistapp.data.WishRepository
import com.example.wishlistapp.data.wishDataBase

object Graph {
    lateinit var dataBase: wishDataBase

    val wishRepository by lazy {
        WishRepository(wishDao = dataBase.wishDao())
    }

    fun provide(context: Context) {
        dataBase = Room.databaseBuilder(context, wishDataBase::class.java, "wishlist.db").build()
    }
}