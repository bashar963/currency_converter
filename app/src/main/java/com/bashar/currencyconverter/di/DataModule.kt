package com.bashar.currencyconverter.di

import android.content.Context
import com.bashar.currencyconverter.data.repository.exchange.ExchangeRepository
import com.bashar.currencyconverter.data.repository.exchange.ExchangeRepositoryImpl
import com.bashar.currencyconverter.data.repository.myBalance.MyBalanceRepository
import com.bashar.currencyconverter.data.repository.myBalance.MyBalanceRepositoryImpl
import com.bashar.currencyconverter.data.service.local.AppDatabase
import com.bashar.currencyconverter.data.service.local.MyBalanceDao
import com.bashar.currencyconverter.data.service.remote.exchange.ExchangeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideExchangeRepository( exchangeService: ExchangeService):ExchangeRepository = ExchangeRepositoryImpl(exchangeService = exchangeService)


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideMyBalanceDao(db: AppDatabase) = db.myBalanceDao()


    @Singleton
    @Provides
    fun provideMyBalanceRepository( myBalanceDao: MyBalanceDao) : MyBalanceRepository = MyBalanceRepositoryImpl(myBalanceDao=myBalanceDao)
}