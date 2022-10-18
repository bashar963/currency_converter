package com.bashar.currencyconverter.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.bashar.currencyconverter.R
import com.bashar.currencyconverter.ui.theme.white

@Composable
fun MainAppBar(title:String) {
    TopAppBar(
        backgroundColor = colorResource(id = R.color.app_bar_color)
    ) {
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                text =  title,
                style = MaterialTheme.typography.h6,
                color = white
            )
        }
    }
}