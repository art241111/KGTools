package com.art241111.kgtools.ui.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.art241111.kgtools.R
import com.art241111.kgtools.ui.views.StartBoldEndNormalText

/**
 * Card for displaying programs.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
@Composable
internal fun ProgramItemCard(
    modifier: Modifier = Modifier,
    index: Int,
    programText: String,
    image: Int,
    onDeleteButton: () -> Unit,
    onCardClick: () -> Unit,
    backgroundColor: Color = Color.Transparent
) {
    CardWithCloseButtonAndIndex(
        modifier = modifier.clickable { onCardClick() },
        index = index,
        onDeleteButton = onDeleteButton,
        backgroundColor = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (image != -1) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 15.dp),
                    painter = painterResource(id = image),
                    contentDescription = null,
                )
            }

            TextOutput(
                modifier = Modifier.weight(1f),
                text = programText
            )
        }
    }
}

@Composable
private fun TextOutput(
    modifier: Modifier,
    text: String
) {
    val lines = text.split("\n")

    val sharedText = mutableListOf<String>()
    lines.forEach {
        sharedText.addAll(it.split(":"))
    }

    if (sharedText.size == 1) {
        Text(modifier = modifier, text = sharedText[0])
    } else {
        Column(modifier) {
            for (i in 0 until sharedText.size step 2) {

                StartBoldEndNormalText(
                    startText = "${sharedText[i]}:",
                    endText = sharedText[i + 1]
                )
            }
        }
    }
}

@Preview
@Composable
fun ProgramItemCardPreview() {
    ProgramItemCard(
        index = 0,
        programText = "Command",
        image = R.drawable.move_to_point,
        onDeleteButton = {},
        onCardClick = {}
    )
}
