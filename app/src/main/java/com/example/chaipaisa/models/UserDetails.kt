package com.example.chaipaisa.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "userdetails")
data class UserDetails(@PrimaryKey(autoGenerate = true) val id:Int,val username:String,val upi_id:String,val transaction_name:String,val paid_or_not:String,val channel_id:String,val amount: Int,
                       val date: String)
