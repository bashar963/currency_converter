package com.bashar.currencyconverter.ui.views.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashar.currencyconverter.data.repository.exchange.ExchangeRepository
import com.bashar.currencyconverter.data.repository.myBalance.MyBalanceRepository
import com.bashar.currencyconverter.model.Commission
import com.bashar.currencyconverter.model.Currency
import com.bashar.currencyconverter.model.ExchangeModel
import com.bashar.currencyconverter.model.MyBalance
import com.bashar.currencyconverter.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val exchangeRepository: ExchangeRepository,
    private val myBalanceRepository: MyBalanceRepository
) : ViewModel() {
    val exchangeResult = mutableStateOf<Resource<ExchangeModel>>(Resource.idle())


    private val _myBalance: MutableState<List<MyBalance>> = mutableStateOf(listOf())
    private var currentConversion = 0
    val myBalance: State<List<MyBalance>> = _myBalance

    init {
       viewModelScope.launch {
           fetchMyBalance()
       }
    }

    private suspend fun fetchMyBalance() {
       /// i had to assign an empty list first then the value cuz the compose was not being update it
       _myBalance.value = listOf()
       _myBalance.value = myBalanceRepository.getMyBalance()

    }


    fun exchange(
        fromAmount: String,
        fromCurrency: String,
        toCurrency: String
    ) =
        viewModelScope.launch {
            exchangeResult.value = Resource.loading()
            exchangeResult.value = exchangeRepository.exchange(fromAmount=fromAmount,fromCurrency=fromCurrency,toCurrency=toCurrency)
        }


    fun applyConvert(sellAmount: Double,sellCurrency: Currency,receiveAmount:Double,receivedCurrency: Currency) : String {

        val fromBalance = myBalance.value.first {
            it.currency == sellCurrency.displayName
        }
        val commissionFee = calcCommissionFee(sellAmount)
        if(fromBalance.amount >= sellAmount){
            currentConversion++
            fromBalance.apply {
                amount -= (sellAmount + commissionFee)
            }
        }else{
            // Means he has no balance to sell
            return "You do not have enough balance to do this action"
        }
        val toBalance = myBalance.value.first {
            it.currency == receivedCurrency.displayName
        }.apply {
            amount += receiveAmount
        }

        viewModelScope.launch {
            myBalanceRepository.updateBalance(fromBalance)
            myBalanceRepository.updateBalance(toBalance)
            fetchMyBalance()
        }

        return  "You have converted ${String.format("%.2f", sellAmount)} ${sellCurrency.displayName} to" +
                " ${String.format("%.2f", receiveAmount)} ${receivedCurrency.displayName}." +
                " Commission Fee - ${String.format("%.2f", commissionFee)} ${sellCurrency.displayName}."
    }

    /**
     * Commission fee can be calculated here
     * since I believe this calculation should be done in BE side so the calculation will be hard coded here
     *
     */
    private  fun calcCommissionFee(amount: Double):Double{
        val commission = Commission(percentage = 0.7, freeUntilAmount = 0.0, freeUntilConversion = 5, currentConversion = currentConversion)
        if(commission.currentConversion <= commission.freeUntilConversion){
            return  0.0
        }
        if(amount <= commission.freeUntilAmount){
            return  0.0
        }

        return (amount * commission.percentage)/100
    }
}