package com.bashar.currencyconverter.ui.views.main

import android.os.Bundle
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bashar.currencyconverter.R
import com.bashar.currencyconverter.model.Currency
import com.bashar.currencyconverter.model.ExchangeType

import com.bashar.currencyconverter.ui.theme.*
import com.bashar.currencyconverter.ui.widgets.CurrencyExchangeWidget
import com.bashar.currencyconverter.ui.widgets.MainAppBar
import com.bashar.currencyconverter.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private var  sellAmount: Double = 1.0
    private var  receiveAmount: Double = 0.0
    private var sellCurrency: Currency = Currency.EUR
    private var receivedCurrency: Currency = Currency.USD


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // default init conversion
            mainViewModel.exchange(fromAmount = sellAmount.toString(), fromCurrency = sellCurrency.displayName, toCurrency =  receivedCurrency.displayName)
            CurrencyConverterTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { MainAppBar(title = stringResource(R.string.app_name)) },
                    bottomBar = {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = Size.padding16,
                                            vertical = Size.padding16
                                        ),
                                    shape = CircleShape,
                                    contentPadding = PaddingValues(all = Size.padding16),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = blue,
                                        contentColor = white,
                                        ),
                                    onClick = {
                                         val message = mainViewModel.applyConvert(
                                                  sellAmount = sellAmount,
                                                  sellCurrency = sellCurrency,
                                                  receiveAmount = receiveAmount,
                                                  receivedCurrency = receivedCurrency
                                              )

                                            Toast.makeText(this, message,
                                                Toast.LENGTH_LONG).show()

                                    },
                                ) {
                                    Text(text = "SUBMIT")
                                }
                    },
                    backgroundColor = MaterialTheme.colors.background
                ) { paddingValues->
                    BodyContent(paddingValues)
                }
            }
        }
    }

    @Composable
    fun BodyContent(paddingValues: PaddingValues) {

        val exchangeResult =  mainViewModel.exchangeResult.value
        val myBalance = remember {
            mainViewModel.myBalance
        }

        var showSellMenu by remember { mutableStateOf(false) }
        var showReceiveMenu by remember { mutableStateOf(false) }

        var sellText by remember { mutableStateOf("1.00") }
        var sellCurrency by remember { mutableStateOf(Currency.EUR.displayName) }

        var receiveCurrency by remember { mutableStateOf(Currency.USD.displayName) }
        var receiveText by remember { mutableStateOf("0.00") }

        if(exchangeResult.status == Resource.Status.SUCCESS ){
            receiveText = exchangeResult.data?.amount ?: "0.00"
            receiveCurrency = exchangeResult.data?.currency ?: Currency.USD.displayName
            receiveAmount = receiveText.toDouble()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    bottom = paddingValues.calculateBottomPadding(),
                    top = paddingValues.calculateTopPadding(),
                )
        ){
            Spacer(modifier = Modifier.height(Size.padding24))
            Text(
                text = "MY BALANCES",
                modifier = Modifier.padding(horizontal = Size.padding16),
                color = grey,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(Size.padding24))
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = Size.padding16)
            ) {


                myBalance.value.forEach {
                        Text(
                            text = String.format("%.2f", it.amount),
                            style = MaterialTheme.typography.h6,
                            color = blue
                        )
                        Text(
                            text = " ${it.currency}",
                            style = MaterialTheme.typography.h6
                        )
                        Spacer(modifier = Modifier.width(Size.padding32))
                    }


            }
            Spacer(modifier = Modifier.height(Size.padding32))
            Text(
                text = "CURRENCY EXCHANGE",
                modifier = Modifier.padding(horizontal = Size.padding16),
                color = grey,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(Size.padding16))
            CurrencyExchangeWidget(
                type = ExchangeType.SELL,
                icon = R.drawable.ic_arrow_up,
                iconContainerColor = red,
                value = sellText,
                currencyPlaceHolder = sellCurrency,
                onValueChange = {
                    sellText = if(it == ""){
                        "0.00"
                    } else if(it.toDoubleOrNull() == null){
                        "0.00"
                    } else{
                        it
                    }
                    sellAmount = sellText.toDoubleOrNull() ?: 0.0
                    mainViewModel.exchange(fromAmount = sellText, fromCurrency = sellCurrency , toCurrency = receiveCurrency)
                },
                showMenu = showSellMenu,
                onDismissRequest = {
                    showSellMenu= false
                },
                onMenuClick = {
                    showSellMenu = !showSellMenu
                },
            ){
                this@MainActivity.sellCurrency = it
                sellCurrency = it.displayName
                showSellMenu =false
                mainViewModel.exchange(fromAmount = sellText, fromCurrency = sellCurrency , toCurrency = receiveCurrency)
            }
            Divider(
                modifier = Modifier.padding(start = Size.padding46)
            )
            Spacer(modifier = Modifier.height(Size.padding16))
            CurrencyExchangeWidget(
                type = ExchangeType.RECEIVE,
                icon = R.drawable.ic_arrow_down,
                iconContainerColor = green,
                value = receiveText,
                currencyPlaceHolder = receiveCurrency,
                onValueChange = {
                    receiveText = it
                },
                showMenu = showReceiveMenu,
                onDismissRequest = {
                    showReceiveMenu= false
                },
                onMenuClick = {
                    showReceiveMenu = !showReceiveMenu
                },
            ){
                this@MainActivity.receivedCurrency = it
                receiveCurrency = it.displayName
                showReceiveMenu =false
                mainViewModel.exchange(fromAmount = sellText, fromCurrency = sellCurrency , toCurrency = receiveCurrency)
            }
            Spacer(modifier = Modifier.height(Size.padding8))
            Divider(
                modifier = Modifier.padding(start = Size.padding46)
            )
        }
    }
}
