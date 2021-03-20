package com.art241111.kgtools.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.art241111.kgtools.R
import com.art241111.kgtools.ui.theme.red500

@Composable
fun TextHeader(
    modifier: Modifier = Modifier,
    text: Int,
    color: Color = Color.White,
    fontFamily: FontFamily? = MaterialTheme.typography.h1.fontFamily,
    fontSize: TextUnit = MaterialTheme.typography.h1.fontSize
) {
    Text(
        modifier = modifier,
        text = stringResource(id = text),
        style = MaterialTheme.typography.h1,
        fontFamily = fontFamily,
        color = color,
        fontSize = fontSize,
        textAlign = TextAlign.Center
    )
}

@Composable
fun StartBoldEndNormalText(
    modifier: Modifier = Modifier,
    startText: String,
    endText: String
) {
    Column(modifier = modifier.padding(2.dp)) {
        Text(
            modifier = Modifier.padding(bottom = 2.dp),
            fontWeight = FontWeight.Bold,
            color = red500,
            text = startText.trim(),
            fontFamily = Font(R.font.geometriabold).toFontFamily(),
            fontSize = 18.sp
        )
        Text(
            text = endText.trim(),
            fontSize = 18.sp
        )
    }
}
