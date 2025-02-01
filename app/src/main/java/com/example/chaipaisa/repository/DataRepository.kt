package com.example.chaipaisa.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chaipaisa.Dao.ChanelDao
import com.example.chaipaisa.Dao.UserDao
import com.example.chaipaisa.Dao.UserdetailsDao
import com.example.chaipaisa.helperfunctions.DeleteResponse
import com.example.chaipaisa.models.ChannelName
import com.example.chaipaisa.models.User
import com.example.chaipaisa.models.UserDetails
import javax.inject.Inject

class DataRepository @Inject constructor(private val userDao: UserDao, private val chanelDao: ChanelDao,private val usertranscdetails:UserdetailsDao) {

    val Allchannels: LiveData<List<ChannelName>>get() = chanelDao.getAllchannel()
    private val _transactionbychannel=MutableLiveData<List<UserDetails>>()
    val transactionbychannel:LiveData<List<UserDetails>>get() = _transactionbychannel

    private val _Allusers = MutableLiveData<List<User>>(emptyList())

    val Allusers:LiveData<List<User>>get() = _Allusers

    suspend fun getTransctnbychannel(upi_id: String){
        _transactionbychannel.postValue(usertranscdetails.selectallpayments(upi_id))
    }
    suspend fun insertpayment_detsils(transctn:UserDetails,callback: (DeleteResponse) -> Unit){
        try {
            callback(DeleteResponse.Loading)
            usertranscdetails.insert_payment_details(transctn)
            callback(DeleteResponse.Success)
        }catch (e:Exception){
            callback(DeleteResponse.Error(e.message.toString()))

        }

    }

//    suspend fun delete_tranc_by_id(ids:List<String>,upi_id: String){
//        usertranscdetails.deletetransaction_id(ids)
//        getTransctnbychannel(upi_id)
//
//    }

    suspend fun delete_tranc_by_id(ids:List<String>,upi_id: String, callback: (DeleteResponse) -> Unit){

        try {
            callback(DeleteResponse.Loading)

            usertranscdetails.deletetransaction_id(ids)
            callback(DeleteResponse.Success)
            

        }catch (e:Exception){
            DeleteResponse.Error("error in db")
        }
    }


    suspend fun AddChanneltoDB(newchannel :ChannelName){
        chanelDao.insert_chanel(newchannel)
    }

    suspend fun AddUsertoGroup(user:User,channelid: String){
        userDao.insert_user(user)
        getUserbychannelId(channelid)


    }

    suspend fun AddUsertoGroup(user:User,){
        userDao.insert_user(user)
    }

    suspend fun getUserbychannelId(channelid:String){

//        val allusers:LiveData<List<User>> =userDao.getAllusers(channelid)
        val allusers =userDao.getAllusers_bychannelid(channelid)
        Log.e("SIZE",allusers.size.toString()+"SIZE VAL")
        chanelDao.updatechannelmenbers(allusers.size.toString(),channelid)


        _Allusers.postValue(allusers)
    }



}