package com.example.chaipaisa.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.chaipaisa.models.User


@Dao
interface UserDao {
    @Insert
    suspend fun insert_user(user: User)


    @Query("Select * from users ")
    suspend  fun getAllusers():List<User>


}