package com.bashar.currencyconverter.data.service.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.bashar.currencyconverter.model.MyBalance

@Dao
interface MyBalanceDao {

    @Query("SELECT * FROM my_balance")
    suspend fun getMyBalance() : List<MyBalance>

    @Update
    suspend fun updateBalance(myBalance: MyBalance)
}