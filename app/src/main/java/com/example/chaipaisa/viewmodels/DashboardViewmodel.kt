package com.example.chaipaisa.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chaipaisa.Dao.ChanelDao
import com.example.chaipaisa.Dao.UserDao
import com.example.chaipaisa.models.ChannelName
import com.example.chaipaisa.models.User
import com.example.chaipaisa.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewmodel @Inject constructor(private val dataRepository: DataRepository):ViewModel() {
    lateinit var  newchannel:ChannelName

    val allchanels :LiveData<List<ChannelName>>get() = dataRepository.Allchannels
    val allusers:LiveData<List<User>>get() = dataRepository.Allusers

    private val _current_Channel_id =MutableLiveData<String>()
    val current_channel_id:LiveData<String>get() = _current_Channel_id


init {
    getAlluser(current_channel_id.value.toString())
}

    fun setcurrent_channnel_id(channel_id:String){

        Log.e("CURRENTCHANNEL ID",channel_id+"here")

        _current_Channel_id.value=channel_id
        Log.e("CURRENTCHANNEL ID",current_channel_id.value+"her33e")
    }



    fun Addnewchannel(channelName: String, channelType: String) {

        newchannel = ChannelName(
            channelName,
            if (channelType == "Personal") 1 else 0,
            channelType,
            0
        )

        viewModelScope.launch {

            dataRepository.AddChanneltoDB(newchannel)

        }


    }

    fun Addnewuser(username:String,upi_id:String,channel_id: String){

        val user:User =User(0,upi_id,username,"",0,0,channel_id)
        viewModelScope.launch {
            dataRepository.AddUsertoGroup(user,channel_id)

        }
//        getAlluser(channel_id)






    }
    fun getAlluser(channel_id:String){
        viewModelScope.launch {  dataRepository.getUserbychannelId(channel_id)}
    }


}