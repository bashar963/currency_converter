package com.bashar.currencyconverter.ui.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bashar.currencyconverter.model.Currency
import com.bashar.currencyconverter.model.ExchangeType
import com.bashar.currencyconverter.ui.theme.Size
import com.bashar.currencyconverter.ui.theme.green

@Composable
fun CurrencyExchangeWidget(
    type: ExchangeType,
    @DrawableRes icon: Int,
    iconContainerColor: Color,
    value: String,
    currencyPlaceHolder:String,
    onValueChange: (String) -> Unit,
    showMenu: Boolean,
    onDismissRequest: () -> Unit,
    onMenuClick: () -> Unit,
    onMenuItemSelect: (selectedCurrency:Currency) -> Unit,
){
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .padding(horizontal = Size.padding16)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ){
        Row( verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier
                .width(32.dp)
                .height(32.dp)
                .background(iconContainerColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(icon),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(Size.padding16))
            Text(
                text = type.displayName
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
           if(type == ExchangeType.RECEIVE)
               Text(
                   text = "+ $value",
                   color = green,
                   fontWeight = FontWeight.Bold,
                   )
               else TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .width(160.dp),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
               keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
               keyboardActions = KeyboardActions(
                   onAny = {
                       focusManager.clearFocus()
                   }
               )
                )
            if(type == ExchangeType.RECEIVE)
                Spacer(modifier = Modifier.width(Size.padding16))
            Row (
                modifier = Modifier
                    .clickable(onClick = onMenuClick)
                    .padding(all = 4.dp),
            )
            {
                Text(text = currencyPlaceHolder)
                Icon(Icons.Default.ArrowDropDown, "ArrowDropDown")
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = onDismissRequest) {
                    Currency.values().forEach {
                        DropdownMenuItem(onClick = {
                            onMenuItemSelect(it)
                        }) {
                            Text(text = it.displayName)
                        }
                    }

                }
            }
        }

    }
}