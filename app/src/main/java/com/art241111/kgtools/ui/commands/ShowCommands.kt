package com.art241111.kgtools.ui.commands

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.art241111.kgtools.R
import com.art241111.kgtools.data.UIMoveToPoint
import com.art241111.kgtools.ui.card.ProgramItemCard
import com.art241111.kgtools.ui.elements.TextHeader
import com.art241111.kgtools.ui.mainScreen.ProgramAndPointsVM

@Composable
internal fun ShowCommands(
    modifier: Modifier = Modifier,
    viewModel: ProgramAndPointsVM
) {
    val programs = viewModel.getCommands()

    if (programs.value != null) {
        if (programs.value!!.isNotEmpty()) {
            LazyColumn(modifier = modifier) {
                itemsIndexed(programs.value!!) { index, item ->
                    var color = Color.Transparent
                    if (item is UIMoveToPoint) {
                        if (!viewModel.getPointsName().value?.contains(item.pointName)!!) {
                            color = Color.Red
                        }
                    }

                    ProgramItemCard(
                        index = index,
                        programText = item.toComposableString(),
                        image = item.image,
                        onDeleteButton = {
                            viewModel.deleteProgram(index)
                        },
                        onCardClick = {
                            viewModel.editCommand(item, index)
                        },
                        backgroundColor = color
                    )
                }
            }
        } else {
            PreviewRobot(
                textId = R.string.program_first_command
            )
        }
    }
}

@Composable
internal fun PreviewRobot(
    modifier: Modifier = Modifier,
    textId: Int
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .alpha(0.5f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
        ) {
            Image(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.robot_preview),
                contentDescription = "Robowizard"
            )

            Spacer(modifier = Modifier.height(20.dp))
            TextHeader(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = textId,
                color = Color.Black
            )
        }
    }
}
