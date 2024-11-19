package com.example.cointracker

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cointracker.dao.SimpleCryptoDAO
import com.example.cointracker.model.SimpleCrypto

@Database(entities = [SimpleCrypto::class], version = 1)
abstract class SimpleCryptoDatabase : RoomDatabase(){
    abstract fun simpleCryptoDao() : SimpleCryptoDAO
}