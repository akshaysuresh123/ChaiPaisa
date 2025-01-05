package com.example.chaipaisa.di

import android.content.Context
import androidx.room.Room
import com.example.chaipaisa.Dao.ChanelDao
import com.example.chaipaisa.Dao.UserDao
import com.example.chaipaisa.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providedatabase(@ApplicationContext context: Context):AppDatabase{
        return Room.databaseBuilder(context,AppDatabase::class.java,"data-base").fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideuserdao(appDatabase: AppDatabase):UserDao{

        return appDatabase.userdao()
    }

    @Provides
    @Singleton
    fun providechanneldao(appDatabase: AppDatabase):ChanelDao{

        return appDatabase.channeldao()
    }

}