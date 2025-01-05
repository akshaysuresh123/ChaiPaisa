package com.example.chaipaisa.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chaipaisa.Dao.ChanelDao
import com.example.chaipaisa.Dao.UserDao
import com.example.chaipaisa.models.ChannelName
import com.example.chaipaisa.models.User
import javax.inject.Inject

class DataRepository @Inject constructor(private val userDao: UserDao, private val chanelDao: ChanelDao) {

    val Allchannels: LiveData<List<ChannelName>>get() = chanelDao.getAllchannel()

    private val _Allusers = MutableLiveData<List<User>>(emptyList())

    val Allusers:LiveData<List<User>>get() = _Allusers


    suspend fun AddChanneltoDB(newchannel :ChannelName){
        chanelDao.insert_chanel(newchannel)
    }

    suspend fun AddUsertoGroup(user:User){
        userDao.insert_user(user)
    }
    suspend fun getUserbychannelId(channelid:String){

//        val allusers:LiveData<List<User>> =userDao.getAllusers(channelid)
        val allusers =userDao.getAllusers()
        Log.e("SIZE",allusers.size.toString()+"SIZE VAL")


        _Allusers.postValue(allusers)
    }



}