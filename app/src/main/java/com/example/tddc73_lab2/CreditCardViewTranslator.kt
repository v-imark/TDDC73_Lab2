package com.example.tddc73_lab2

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText

fun formatText(text: String, bank: Int): String {
    val max = if (bank == R.drawable.amex) 15
    else 16

    val trimmed = if (text.length >= max) {
        val rangeMax = max - 1
        text.substring(0..rangeMax)
    } else text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (bank == R.drawable.amex) {
            if (i == 3 || i == 9) out += " "
        } else {
            if (i % 4 == 3 && i != 15) out += " "
        }

    }
    return out
}

fun standardOTT(offset: Int): Int {
    if (offset <= 3) return offset
    if (offset <= 7) return offset + 1
    if (offset <= 11) return offset + 2
    if (offset <= 16) return offset + 3
    return 19
}

fun standardTTO(offset: Int): Int {
    if (offset <= 4) return offset
    if (offset <= 9) return offset - 1
    if (offset <= 14) return offset - 2
    if (offset <= 19) return offset - 3
    return 16
}

fun amexOTT(offset: Int): Int {
    if (offset <= 3) return offset
    if (offset <= 9) return offset + 1
    if (offset <= 14) return offset + 2
    return 17
}

fun amexTTO(offset: Int): Int {
    if (offset <= 4) return offset
    if (offset <= 10) return offset - 1
    if (offset <= 17) return offset - 2
    return 15
}


fun creditCardViewTranslator(text: AnnotatedString, bank: Int): TransformedText {

    // Making XXXX-XXXX-XXXX-XXXX string.
    // Amex: XXXX XXXXXX XXXXX
    val out = formatText(text.text, bank)

    /**
     * The offset translator should ignore the hyphen characters, so conversion from
     *  original offset to transformed text works like
     *  - The 4th char of the original text is 5th char in the transformed text.
     *  - The 13th char of the original text is 15th char in the transformed text.
     *  Similarly, the reverse conversion works like
     *  - The 5th char of the transformed text is 4th char in the original text.
     *  - The 12th char of the transformed text is 10th char in the original text.
     */
    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (bank == R.drawable.amex) return amexOTT(offset)
            return standardOTT(offset)
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (bank == R.drawable.amex) return amexTTO(offset)
            return standardTTO(offset)
        }
    }

    return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}