package com.example.tddc73_lab2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class CardSide {
    Front,
    Back
}

enum class CardFocus {
    NoFocus,
    CardNumber,
    CardHolder,
    CardExpires,
}


data class MyState(val side: CardSide = CardSide.Front)


class CardViewModel {
    private val _uiState = MutableStateFlow(MyState())
    val uiState = _uiState.asStateFlow()

    var cardNumberFocusRequester = FocusRequester()
    var cardHolderFocusRequester = FocusRequester()
    var expiresFocusRequester = FocusRequester()

    var bankShown by mutableStateOf(R.drawable.visa)
        private set
    var currentFocus by mutableStateOf(CardFocus.CardNumber)
        private set

    var cardNumber by mutableStateOf("")
        private set

    var cardHolder by mutableStateOf("")
        private set

    var cardCvv by mutableStateOf("")
        private set
    var cardMonth by mutableStateOf("")
        private set

    var cardYear by mutableStateOf("")
        private set

    fun changeCardSide(cvvIsFocused: Boolean) {
        _uiState.update { currentState ->
            when (cvvIsFocused) {
                true -> currentState.copy(side = CardSide.Back)
                false -> currentState.copy(side = CardSide.Front)
            }
        }
    }

    fun setFocus(boxToFocus: CardFocus) {
        currentFocus = boxToFocus
    }


    fun changeCardNumber(number: String) {
        cardNumber = number

        var re = Regex("^4")
        if (number.take(1).matches(re)) changeBankShown(R.drawable.visa)
        re = Regex("^(34|37)")
        if (number.take(2).matches(re)) changeBankShown(R.drawable.amex)
        re = Regex("^5[1-5]")
        if (number.take(2).matches(re)) changeBankShown(R.drawable.mastercard)
        re = Regex("^6011")
        if (number.take(4).matches(re)) changeBankShown(R.drawable.discover)
        re = Regex("^9792")
        if (number.take(4).matches(re)) changeBankShown(R.drawable.troy)
    }

    fun changeCardHolder(name: String) {
        cardHolder = name
    }

    fun changeCardCvv(cvv: String) {
        cardCvv = cvv
    }

    fun changeCardMonth(month: String) {
        cardMonth = month
    }

    fun changeCardYear(year: String) {
        cardYear = year
    }

    private fun changeBankShown(bankRef: Int) {
        bankShown = bankRef
    }
}