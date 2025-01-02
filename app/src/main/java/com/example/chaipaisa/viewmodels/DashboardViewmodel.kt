package com.example.chaipaisa.viewmodels

import androidx.lifecycle.ViewModel
import com.example.chaipaisa.Dao.ChanelDao
import com.example.chaipaisa.Dao.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DashboardViewmodel @Inject constructor(private val userDao: UserDao,private val chanelDao: ChanelDao):ViewModel() {


}