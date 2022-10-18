package com.bashar.currencyconverter.data.service.remote.exchange

import com.bashar.currencyconverter.model.ExchangeModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeService {

    @GET("/currency/commercial/exchange/{fromAmount}-{fromCurrency}/{toCurrency}/latest")
    suspend fun exchange(@Path(value = "fromAmount") fromAmount:String,@Path(value = "fromCurrency") fromCurrency:String,@Path(value = "toCurrency") toCurrency:String) : Response<ExchangeModel>

}