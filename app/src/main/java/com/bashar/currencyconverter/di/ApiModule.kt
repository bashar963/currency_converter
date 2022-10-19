package com.bashar.currencyconverter.di

import com.bashar.currencyconverter.data.service.remote.exchange.ExchangeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {


    @Provides
    fun provideExchangeService(): ExchangeService {
        return Retrofit.Builder()
            .baseUrl("http://api.evp.lt")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeService::class.java)
    }
}