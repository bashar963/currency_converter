package com.bashar.currencyconverter.data.repository.exchange

import com.bashar.currencyconverter.model.ExchangeModel
import com.bashar.currencyconverter.utils.Resource


interface ExchangeRepository {

    suspend fun exchange(fromAmount:String,fromCurrency:String,toCurrency:String) : Resource<ExchangeModel>

}