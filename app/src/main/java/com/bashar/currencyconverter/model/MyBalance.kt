package com.bashar.currencyconverter.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_balance")
data class MyBalance(
    @PrimaryKey
    val id: Int,
    val currency: String,
    var amount: Double,
)