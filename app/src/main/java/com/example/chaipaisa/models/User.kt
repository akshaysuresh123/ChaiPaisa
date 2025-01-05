package com.example.chaipaisa.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(@PrimaryKey(autoGenerate =true) val id:Int ,val upi_id:String,val name:String,val status:String,val take:Int,val give:Int,val channel_id:String)


