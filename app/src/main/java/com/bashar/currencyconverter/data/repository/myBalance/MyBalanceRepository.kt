package com.bashar.currencyconverter.data.repository.myBalance

import com.bashar.currencyconverter.model.MyBalance

interface MyBalanceRepository {

    suspend fun getMyBalance() : List<MyBalance>
    suspend fun updateBalance(myBalance: MyBalance)
}