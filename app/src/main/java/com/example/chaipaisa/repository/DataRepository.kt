package com.example.chaipaisa.repository

import androidx.lifecycle.LiveData
import com.example.chaipaisa.Dao.ChanelDao
import com.example.chaipaisa.Dao.UserDao
import com.example.chaipaisa.models.ChannelName
import javax.inject.Inject

class DataRepository @Inject constructor(private val userDao: UserDao, private val chanelDao: ChanelDao) {

    val Allchannels: LiveData<List<ChannelName>>get() = chanelDao.getAllchannel()


    suspend fun AddChanneltoDB(newchannel :ChannelName){
        chanelDao.insert_chanel(newchannel)
    }

}