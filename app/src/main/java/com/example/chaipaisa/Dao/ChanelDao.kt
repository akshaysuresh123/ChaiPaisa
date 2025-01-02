package com.example.chaipaisa.Dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.chaipaisa.models.ChannelName

@Dao
interface ChanelDao {
    @Insert
    suspend fun insert_chanel(chanel:ChannelName)
}