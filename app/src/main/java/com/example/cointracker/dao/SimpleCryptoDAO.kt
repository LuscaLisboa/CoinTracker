package com.example.cointracker.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cointracker.model.SimpleCrypto

@Dao
interface SimpleCryptoDAO {

    @Insert
    suspend fun insert(simpleCrypto: SimpleCrypto)

    @Query("SELECT * FROM simpleCrypto")
    suspend fun listAll() : List<SimpleCrypto>

    @Query("DELETE FROM simpleCrypto WHERE id = :id")
    suspend fun delete(id: String)
}