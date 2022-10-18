package com.bashar.currencyconverter.data.repository.myBalance

import com.bashar.currencyconverter.data.service.local.MyBalanceDao
import com.bashar.currencyconverter.model.MyBalance
import javax.inject.Inject

class MyBalanceRepositoryImpl @Inject constructor(private val myBalanceDao: MyBalanceDao): MyBalanceRepository  {


    override suspend fun getMyBalance(): List<MyBalance> = myBalanceDao.getMyBalance()
    override suspend fun updateBalance(myBalance: MyBalance) =  myBalanceDao.updateBalance(myBalance)

}