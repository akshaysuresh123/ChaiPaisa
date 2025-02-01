package com.example.chaipaisa.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chaipaisa.Dao.ChanelDao
import com.example.chaipaisa.Dao.UserDao
import com.example.chaipaisa.Dao.UserdetailsDao
import com.example.chaipaisa.models.ChannelName
import com.example.chaipaisa.models.User
import com.example.chaipaisa.models.UserDetails

@Database(entities = [User::class,ChannelName::class,UserDetails::class], version = 9)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userdao():UserDao
    abstract fun channeldao():ChanelDao
    abstract  fun userpaymentdao():UserdetailsDao
}