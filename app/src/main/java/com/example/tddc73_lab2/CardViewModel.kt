package com.example.tddc73_lab2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class CardSide {
    Front,
    Back
}

data class MyState(val side: CardSide = CardSide.Front, val cardNumber: String = "#### #### #### ####")

class CardViewModel {
    private val _uiState = MutableStateFlow(MyState())
    val uiState = _uiState.asStateFlow()

    var cardNumber by mutableStateOf("")
        private set

    var numberFocused by mutableStateOf(false)

    fun changeCardSide() {
        _uiState.update { currentState ->
            when (currentState.side) {
                CardSide.Front -> currentState.copy(side = CardSide.Back)
                CardSide.Back -> currentState.copy(side = CardSide.Front)
            }
        }
    }

    fun changeCardNumber(number: String) {
        println(number.length)
        cardNumber = number

        if (cardNumber.length % 4 == 0 && cardNumber.isNotEmpty()) {
            cardNumber.padEnd(1, ' ')
        }
    }

    fun setNumberFocus(isFocused:Boolean){
        numberFocused = isFocused
    }
}