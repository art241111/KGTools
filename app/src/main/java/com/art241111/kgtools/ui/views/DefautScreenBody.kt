package com.art241111.kgtools.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.art241111.kgtools.R

@Composable
internal fun DefaultScreenBody(
    modifier: Modifier = Modifier,
    settingsNavigate: () -> Unit = {},
    body: @Composable () -> Unit
) {
    var clickCount = remember { 0 }
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .verticalGradientBackground(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clickable {
                    if (++clickCount == 2) {
                        clickCount = 0
                        settingsNavigate()
                    }
                },
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h2,
            color = Color.White
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
        ) {
            body()
        }
    }
}

@Preview
@Composable
private fun DefaultScreenBodyPreview() {
    DefaultScreenBody() {
        Text(text = "a")
    }
}
