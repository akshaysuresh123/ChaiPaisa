package com.example.chaipaisa.Dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.chaipaisa.models.User


@Dao
interface UserDao {
    @Insert
    suspend fun insert_user(user: User)

}