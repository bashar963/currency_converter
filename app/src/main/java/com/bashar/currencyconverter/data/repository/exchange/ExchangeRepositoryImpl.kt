package com.bashar.currencyconverter.data.repository.exchange

import com.bashar.currencyconverter.data.service.remote.exchange.ExchangeService
import com.bashar.currencyconverter.model.ExchangeModel
import com.bashar.currencyconverter.utils.BaseDataSource
import com.bashar.currencyconverter.utils.Resource
import javax.inject.Inject

class ExchangeRepositoryImpl @Inject constructor(private val exchangeService: ExchangeService): ExchangeRepository, BaseDataSource() {

    override suspend fun exchange(
        fromAmount: String,
        fromCurrency: String,
        toCurrency: String
    ): Resource<ExchangeModel> = getResult {
        exchangeService.exchange(
            fromAmount = fromAmount,
            fromCurrency = fromCurrency,
            toCurrency = toCurrency
        )
    }
}