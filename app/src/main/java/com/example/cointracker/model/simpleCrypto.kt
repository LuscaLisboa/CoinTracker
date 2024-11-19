package com.example.cointracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "simpleCrypto")
data class SimpleCrypto(
    @PrimaryKey(autoGenerate = false) val id: String,
    val symbol: String,
    val name: String,
    val image: String
)