package com.example.tddc73_lab2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class CardSide {
    Front,
    Back
}

enum class CardFocus {
    CardName,
    CardHolder,
    CardExpires,
}

data class MyState(val side: CardSide = CardSide.Front)

class CardViewModel {
    private val _uiState = MutableStateFlow(MyState())
    val uiState = _uiState.asStateFlow()

    var cardNumber by mutableStateOf("")
        private set

    var numberFocused by mutableStateOf(false)

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

    fun changeCardNumber(number: String) {
        cardNumber = number
    }

    fun changeCardHolder(name: String) {
        cardHolder = name
    }

    fun changeCardCvv(cvv: String) {
        cardCvv = cvv
    }

    fun setNumberFocus(isFocused: Boolean) {
        numberFocused = isFocused
    }

    fun changeCardMonth(month: String) {
        cardMonth = month
    }

    fun changeCardYear(year: String) {
        cardYear = year
    }
}