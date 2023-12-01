package com.example.tddc73_lab2

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateIntSizeAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.zIndex
import kotlin.math.floor
import kotlin.math.roundToInt

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedBorder(focus: CardFocus,
                   numberOffset: Offset, numberSize:IntSize,
                   holderOffset:Offset, holderSize:IntSize,
                   expiresOffset:Offset, expiresSize:IntSize
) {
    var moved by remember { mutableStateOf(false) }
    var outsidePad = 12.dp
    var textPadHorz = 24.dp

    //Create a variable to adjust for previous padding
    val adjustForPad = with(LocalDensity.current)
    {
        IntOffset(
            outsidePad.toPx().toInt()-textPadHorz.toPx().toInt()
            , -outsidePad.toPx().toInt())
    }
    fun getOffset():IntOffset {
        var offset = IntOffset.Zero
        if (focus == CardFocus.CardNumber) {
            //Get position and add on the adjustment
            offset = IntOffset(numberOffset.x.toInt(), numberOffset.y.toInt())+adjustForPad
        }else if(focus == CardFocus.CardHolder) {
            offset = IntOffset(holderOffset.x.toInt(), holderOffset.y.toInt()) + adjustForPad
        }else if(focus == CardFocus.CardExpires){
            offset = IntOffset(expiresOffset.x.toInt(), expiresOffset.y.toInt()) + adjustForPad
        } else {
            offset = IntOffset(numberOffset.x.toInt(), numberOffset.y.toInt())
        }
        return offset
    }
    //Create the correct offset for the animation
    val offset by animateIntOffsetAsState(
        targetValue = getOffset(),
        label = "offset"
    )

    //Assign the correct size for the animation from the parameters
    val boxSize by animateIntSizeAsState(
        targetValue =
        if (focus == CardFocus.CardNumber) {
            numberSize
        } else if(focus == CardFocus.CardHolder) {
            holderSize
        }else if(focus == CardFocus.CardExpires){
            expiresSize
        }else {
            IntSize.Zero
        },
        label = "size"
    )

    Box(
        modifier = Modifier
            .offset {
               offset
            }.clip(RoundedCornerShape(10.dp))
            .size(((boxSize.width.dp)/3), ((boxSize.height.dp)/3))
            .background(Color(1, 1, 1, 150))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            )
            {
                moved = !moved
            }.border( width = 3.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(10.dp))
    )
}