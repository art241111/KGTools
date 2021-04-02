package com.art241111.kgtools.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.art241111.kcontrolsystem.ui.theme.red500

@Composable
fun TabButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit
) {
    if (isSelected) {
        TabButtonDefault(
            modifier = modifier.clickable(onClick = onClick),
            text = text,
            backgroundColor = red500,
            fontColor = Color.White
        )
    } else {
        TabButtonDefault(
            modifier = modifier.clickable(onClick = onClick),
            text = text,
        )
    }
}

@Composable
private fun TabButtonDefault(
    modifier: Modifier = Modifier,
    text: String,
    fontColor: Color = Color.Unspecified,
    backgroundColor: Color = MaterialTheme.colors.background
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        backgroundColor = backgroundColor
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            text = text,
            color = fontColor
        )
    }
}
