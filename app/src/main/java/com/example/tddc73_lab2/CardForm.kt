package com.example.tddc73_lab2

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat.ScrollIndicators
import java.time.Year

@Composable
fun CardForm(viewModel: CardViewModel) {
    val months = (1..12).map { it.toString().padStart(2, '0') }
    val currentYear = Year.now().value
    val years = (currentYear..currentYear + 10).map { it.toString() }
    val maxNumberLength = 16
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomTextField(
            label = "Card Number",
            value = viewModel.cardNumber,
            onValueChange = { number -> viewModel.changeCardNumber(number)
                            if(number.length == maxNumberLength){ viewModel.setFocus(CardFocus.CardHolder) }},
            visualTransformation = { text -> creditCardViewTranslator(text) },
            keyboardType = KeyboardType.NumberPassword,
            modifier = Modifier.focusRequester(viewModel.cardNumberFocusRequester)
                .onFocusChanged{ if (it.isFocused) {
                viewModel.setFocus(CardFocus.CardNumber)} },
        )
        CustomTextField(
            label = "Card Holder",
            value = viewModel.cardHolder,
            onValueChange = { name -> viewModel.changeCardHolder(name) },
            capitalization = KeyboardCapitalization.Characters,
            modifier = Modifier.onFocusChanged{ if (it.isFocused) {
                       viewModel.setFocus(CardFocus.CardHolder)} }.focusRequester(viewModel.cardHolderFocusRequester)

        )
        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            CustomDropdownMenu(
                label = "Month",
                value = viewModel.cardMonth,
                modifier = Modifier.weight(2f)
                    .onFocusChanged{ if (it.isFocused) {
                    viewModel.setFocus(CardFocus.CardExpires)}}.focusRequester(viewModel.expiresFocusRequester),
                list = months,
                onSelect = { month -> viewModel.changeCardMonth(month) },

            )
            CustomDropdownMenu(
                label = "Year",
                value = viewModel.cardYear,
                modifier = Modifier.weight(2f)
                    .onFocusChanged{ if (it.isFocused) {
                    viewModel.setFocus(CardFocus.CardExpires)} },
                list = years,
                onSelect = { year -> viewModel.changeCardYear(year) }
            )

            CustomTextField(
                modifier = Modifier
                    .weight(1.5f)
                    .onFocusChanged { focusState ->
                        viewModel.changeCardSide(focusState.isFocused)
                    },
                label = "CVV",
                value = viewModel.cardCvv,
                onValueChange = { cvv -> if (cvv.length <= 3) viewModel.changeCardCvv(cvv) },
                keyboardType = KeyboardType.NumberPassword,
            )

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
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    readOnly: Boolean = false,
    trailingIcon: @Composable() (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = capitalization
        ),
        visualTransformation = visualTransformation,
        modifier = modifier.fillMaxWidth(),
        readOnly = readOnly,
        trailingIcon = trailingIcon,

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownMenu(
    label: String,
    value: String,
    modifier: Modifier,
    list: List<String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        CustomTextField(
            label = label,
            value = value,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.menuAnchor(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .height(200.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Divider(modifier = Modifier.fillMaxWidth())
            list.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onSelect(item)
                        expanded = false
                    }
                )
                Divider(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}