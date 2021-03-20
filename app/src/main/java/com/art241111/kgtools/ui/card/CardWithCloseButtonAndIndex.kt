package com.art241111.kgtools.ui.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.art241111.kgtools.R

@Composable
internal fun CardWithCloseButtonAndIndex(
    modifier: Modifier = Modifier,
    index: Int,
    onDeleteButton: () -> Unit,
    backgroundColor: Color = Color.Transparent,
    content: @Composable() (BoxScope.() -> Unit),
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        elevation = 0.dp,
        backgroundColor = backgroundColor
    ) {
        Box(
            modifier = Modifier.padding(end = 15.dp),
            contentAlignment = Alignment.CenterStart
        ) {

            content()

            IconButton(
                modifier = Modifier
                    .size(30.dp)
                    .padding(top = 15.dp)
                    .align(Alignment.TopEnd),
                onClick = { onDeleteButton() }
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter =
                    painterResource(id = R.drawable.ic_baseline_close_48_grey),
                    contentDescription = null
                )
            }

            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 11.dp, bottom = 15.dp),
                text = (index + 1).toString()
            )
        }
    }
}
