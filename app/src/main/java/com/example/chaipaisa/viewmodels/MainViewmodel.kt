package com.example.chaipaisa.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewmodel:ViewModel() {

    private var _flag: MutableLiveData<Boolean> = MutableLiveData(false)

    val flag :LiveData<Boolean>
        get()=_flag

    init {

        viewModelScope.launch {
            delay(3000)
            _flag.postValue(true)
        }


    }


}