package com.example.chaipaisa.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chaipaisa.Dao.ChanelDao
import com.example.chaipaisa.Dao.UserDao
import com.example.chaipaisa.models.ChannelName
import com.example.chaipaisa.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewmodel @Inject constructor(private val dataRepository: DataRepository):ViewModel() {
    lateinit var  newchannel:ChannelName

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


}