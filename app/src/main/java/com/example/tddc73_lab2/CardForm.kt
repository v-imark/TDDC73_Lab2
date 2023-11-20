package com.example.tddc73_lab2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CardForm(viewModel: CardViewModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomTextField(
            label = "Card Number",
            value = viewModel.cardNumber,
            onValueChange = { number -> viewModel.changeCardNumber(number) },
            visualTransformation = { text -> creditCardViewTranslator(text) },
            keyboardType = KeyboardType.NumberPassword
        )
        CustomTextField(
            label = "Card Holder",
            value = viewModel.cardHolder,
            onValueChange = { name -> viewModel.changeCardHolder(name)},
            capitalization = KeyboardCapitalization.Characters
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(2f)) {

            }
            CustomTextField(
                modifier = Modifier.weight(1f),
                label = "CVV",
                value = viewModel.cardCvv,
                onValueChange = { cvv -> viewModel.changeCardCvv(cvv)})
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
        modifier: Modifier = Modifier,
        label: String,
        value: String,
        onValueChange: (String) -> Unit,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        keyboardType: KeyboardType = KeyboardType.Text,
        capitalization: KeyboardCapitalization = KeyboardCapitalization.None
) {
    val focusRequester = remember { FocusRequester() }
    OutlinedTextField(
        value = viewModel.cardNumber,
        onValueChange = { number ->  viewModel.changeCardNumber(number)},
        label = { Text("Card Number") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        modifier = Modifier.fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged{ if (it.isFocused) {
                viewModel.setNumberFocus(true)
            } else {
                viewModel.setNumberFocus(false)
            } },

    )
}