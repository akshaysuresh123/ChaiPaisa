package com.example.chaipaisa.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.chaipaisa.models.ChannelName

@Dao
interface ChanelDao {
    @Insert
    suspend fun insert_chanel(chanel:ChannelName)

    @Query("Select * from Chanels")
    fun getAllchannel():LiveData<List<ChannelName>>

    @Query("Update Chanels set activemembers=:activemembers where channel_name =:channel_id")
    suspend fun updatechannelmenbers(activemembers:String,channel_id:String)


}