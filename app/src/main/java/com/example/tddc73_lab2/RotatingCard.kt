package com.example.tddc73_lab2

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultCameraDistance
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlin.random.Random

@Composable
fun RotatingCard(viewModel: CardViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    val transition = updateTransition(uiState, label = "card_state")

    val deg by transition.animateFloat(
        label = "card_state",
        transitionSpec = { tween(durationMillis = 1000) }
    ) { state ->
        when (state.side) {
            CardSide.Front -> 0.0f
            CardSide.Back -> 180.0f
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(
                rotationY = deg,
                cameraDistance = DefaultCameraDistance * 2.0f,
            )
            .shadow(elevation = 20.dp, shape = RoundedCornerShape(8))
            .clip(RoundedCornerShape(8))
    ) {
        Image(
            painter = painterResource(id = R.drawable._10),
            contentDescription = "CreditCard",
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.tint(
                Color(0, 0, 0, 70),
                blendMode = BlendMode.Darken
            ),
            modifier = Modifier
                .zIndex(0.0f)
                .fillMaxWidth()
        )
        if (deg > 90.0f) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .zIndex(1.0f)
                    .graphicsLayer(rotationY = 180.0f)
            ) {
                Text("Back", color = Color.White, fontSize = 30.sp)
            }
        } else {
            Box(
                modifier = Modifier
                    .matchParentSize()
            ) {
                FrontSide(viewModel.cardNumber, viewModel.cardHolder, viewModel.numberFocused, )
            }
        }
    }

}


@Composable
fun FrontSide(number: String, name: String,isFocused: Boolean ) {

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(22.dp)
            .fillMaxSize()
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.chip),
                contentDescription = "CardChip",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.height(50.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.visa),
                contentDescription = "Visa",
                modifier = Modifier.height(50.dp)
            )
        }
        Text(
            text = formatText(number),
            color = Color.White,
            fontWeight = FontWeight(600),
            fontFamily = FontFamily.Monospace,
            fontSize = 6.em,
        )

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            TextWithTitle(title = "Card Holder", text = name)
            TextWithTitle(title = "Expires", text = "MM/YY")
        }
    }

}

fun addBorder(isFocused:Boolean): Modifier {
    if(isFocused){
        return Modifier.border(BorderStroke(2.dp, Color.White))
    }else{
      return Modifier.border(BorderStroke(0.dp, Color.White))
    }
}

@Composable
fun TextWithTitle(title: String, text: String) {
    Column {
        Text(
            text = title,
            color = Color.White,
            fontWeight = FontWeight(600),
            fontFamily = FontFamily.Monospace,
            fontSize = 3.2.em,
            modifier = Modifier.alpha(0.7f)
        )
        Text(
            text = text.uppercase(),
            color = Color.White,
            fontWeight = FontWeight(600),
            fontFamily = FontFamily.Monospace,
            fontSize = 4.5.em,
        )
    }
}