package com.bashar.currencyconverter.model



data class Commission(
    val percentage: Double,
    var freeUntilAmount : Double,
    var freeUntilConversion : Int,
    var currentConversion : Int,
)