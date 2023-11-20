package com.example.tddc73_lab2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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

enum class CardFocus {
    CardName,
    CardHolder,
    CardExpires,
}

data class MyState(val side: CardSide = CardSide.Front, val cardNumber: String = "#### #### #### ####")

class CardViewModel {
    private val _uiState = MutableStateFlow(MyState())
    val uiState = _uiState.asStateFlow()

    var cardNumber by mutableStateOf("")
        private set

    var cardHolder by mutableStateOf("")
        private set

    var cardCvv by mutableStateOf("")
        private set

    fun changeCardSide() {
        _uiState.update { currentState ->
            when (currentState.side) {
                CardSide.Front -> currentState.copy(side = CardSide.Back)
                CardSide.Back -> currentState.copy(side = CardSide.Front)
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

}