package com.example.chaipaisa.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Chanels")
data class ChannelName(@PrimaryKey val channel_name:String,val activemembers:Int,val channel_type:String,val net_total:Int)
