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

    @Query("Select * from users where channel_id = :channelId")
    suspend  fun getAllusers_bychannelid(channelId:String):List<User>

    @Query("Select * from users where upi_id = :upi_id")
    suspend  fun getuser(upi_id:String):User


}