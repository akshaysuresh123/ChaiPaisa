package com.example.chaipaisa.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chaipaisa.Dao.UserDao
import com.example.chaipaisa.Dao.UserdetailsDao
import com.example.chaipaisa.helperfunctions.DeleteResponse
import com.example.chaipaisa.models.User
import com.example.chaipaisa.models.UserDetails
import com.example.chaipaisa.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleUserViewModel @Inject constructor(val dataRepository: DataRepository,val userDao: UserDao,val userdetailsDao: UserdetailsDao):ViewModel() {

    private var _userDetail: MutableLiveData<User> = MutableLiveData()
    val userDetail: LiveData<User> = _userDetail

    val transactions: LiveData<List<UserDetails>>
        get() = dataRepository.transactionbychannel

    private var _totalsum:MutableLiveData<Int> = MutableLiveData()

    val totalsum: LiveData<Int> = _totalsum




    fun deleteSelectedTransactions(selectedTransactions: SnapshotStateList<UserDetails>,upi_id: String) {
//        val updatedTransactions = transactions.value?.filter { it !in selectedTransactions }
        var idstodelete= selectedTransactions.map { it.id.toString()}
        viewModelScope.launch {
            dataRepository.delete_tranc_by_id(idstodelete,upi_id){
                deleteResponse ->
                when(deleteResponse){
                    is DeleteResponse.Loading->{
                        Log.e("RESPONSE","loadinggg")
                    }
                    is DeleteResponse.Success->{
                        fetchtotalsum(upi_id)
                        Log.e("RESPONSE","sucess")
                        viewModelScope.launch { fetchuser(upi_id) }

                    }
                    is DeleteResponse.Error->{

                        Log.e("RESPONSE",deleteResponse.message )
                    }
                    else->{
                        Log.e("RESPONSE","NO RESPONSE")
                    }

                }
            }
        }


       selectedTransactions.clear()
    }




    fun addTransaction(transctn:UserDetails){
        viewModelScope.launch {
            dataRepository.insertpayment_detsils(transctn){deleteResponse ->

                when(deleteResponse){

                    is DeleteResponse.Loading->{
                        Log.e("Response","Loading add")
                    }
                    is DeleteResponse.Success->{
                        viewModelScope.launch { dataRepository.getTransctnbychannel(transctn.upi_id) }
                        fetchtotalsum(transctn.upi_id)
                        Log.e("Response","added successful")
                    }
                    is DeleteResponse.Error->{

                        Log.e("RESPONSE",deleteResponse.message)

                    }
                    else->{
                        Log.e("Response","NOT WORKING")
                    }
                }

            }

        }


        }



    fun fetchtotalsum(upi_id: String){
        viewModelScope.launch {
            _totalsum.postValue(userdetailsDao.getTotalAmountByUpiId(upi_id))
        }


    }

    suspend fun fetchuser(upi_id:String){

        val userd=userDao.getuser(upi_id)
        _userDetail.value=userd

        dataRepository.getTransctnbychannel(upi_id)
        fetchtotalsum(upi_id)

    }

}