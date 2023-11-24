package com.example.tddc73_lab2

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedText(
    text: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit
) {
    var oldText by remember { mutableStateOf(text) }

    SideEffect {
        oldText = text
    }

    Row(modifier = modifier) {
        for (i in placeholder.indices) {
            val oldChar = oldText.getOrNull(i)
            val newChar = text.getOrNull(i)
            val char = if (oldChar == newChar) {
                oldChar ?: placeholder[i]
            } else {
                newChar ?: placeholder[i]
            }
            AnimatedContent(
                targetState = char,
                label = "",
                transitionSpec = {
                    slideInVertically { -it } with slideOutVertically { it }
                }
            ) { c ->
                Text(
                    text = c.toString(),
                    softWrap = false,
                    fontSize = fontSize,
                    color = Color.White,
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily.Monospace,
                )
            }

        }
    }
}

