package com.example.wishlistapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
abstract class WishDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addAwish(wishEntity: Wish)

    @Query("Select * from `wish-table`")
    abstract  fun getallWishes():Flow<List<Wish>>


    @Update
    abstract suspend fun updatewish(wishEntity: Wish)

    @Delete
    abstract suspend  fun deletewish(wishEntity: Wish)


    @Query("Select  * from `wish-table` where id=:id")
    abstract  fun getAWisheshById(id: Long) : Flow<Wish>
}