package com.example.wishlistapp.data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDao: WishDao ) {

    suspend fun addWish(wish: Wish) {
        wishDao.addAwish(wish)
    }

    fun getWish(): Flow<List<Wish>> = wishDao.getallWishes()

    fun getWishByID(id: Long): Flow<Wish> = wishDao.getAWisheshById(id)

    suspend fun updateWish(wish: Wish) {
        wishDao.updatewish(wish)
    }

    suspend fun deleteWish(wish: Wish) {
        wishDao.deletewish(wish)
    }


}