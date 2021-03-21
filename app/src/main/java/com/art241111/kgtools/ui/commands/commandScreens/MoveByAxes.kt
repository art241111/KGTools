package com.art241111.kgtools.ui.commands.commandScreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.art241111.kgtools.R
import com.art241111.kgtools.data.UIMoveByAxes
import com.art241111.kgtools.ui.commands.ProgramNavigateVm
import com.art241111.kgtools.ui.views.Spinner
import com.art241111.kgtools.ui.mainScreen.ProgramAndPointsVM
import com.github.poluka.kControlLibrary.enity.Axes

@Composable
internal fun ShowMoveByAxes(
    programAndPointsVM: ProgramAndPointsVM,
    navigate: ProgramNavigateVm,
    editCommand: UIMoveByAxes?,
    closeProgramMenu: () -> Unit
) {

    val axesValues = Axes.values()
    val axes = mutableListOf<String>()
    axesValues.forEach {
        axes.add(it.name)
    }

    var defaultAxes = if (axes.isNotEmpty()) axes[0] else ""
    var defaultDistance = "0.0"
    if (editCommand != null) {
        defaultAxes = editCommand.axes.toString()
        defaultDistance = editCommand.distance.toString()
    }

    val textAxes = remember { mutableStateOf(defaultAxes) } // initial value
    Spinner(
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .padding(top = 40.dp),
        label = stringResource(id = R.string.command_move_by_axes_select_axes),
        list = axes,
        text = textAxes
    )

    var distance by remember { mutableStateOf(defaultDistance) } // initial value
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        value = distance,
        onValueChange = { distance = it },
        label = { Text(text = stringResource(id = R.string.command_distance)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                defaultKeyboardAction(ImeAction.Done)

                addCommand(
                    editCommand = editCommand,
                    programAndPointsVM = programAndPointsVM,
                    textAxes = textAxes,
                    distance = distance,
                    closeProgramMenu = closeProgramMenu
                )
            }
        )
    )

    Spacer(modifier = Modifier.height(10.dp))
    Button(
        onClick = {
            addCommand(
                editCommand = editCommand,
                programAndPointsVM = programAndPointsVM,
                textAxes = textAxes,
                distance = distance,
                closeProgramMenu = closeProgramMenu
            )
        }
    ) {
        if (editCommand == null) {
            Text(text = stringResource(id = R.string.add_command))
        } else {
            Text(text = stringResource(id = R.string.edit_command))
        }
    }

    Spacer(modifier = Modifier.height(10.dp))
    Button(
        onClick = {
            programAndPointsVM.uiCommandUpgrade = null
            navigate.back()
        }
    ) {
        Text(text = stringResource(id = R.string.cancel))
    }
    Spacer(modifier = Modifier.height(10.dp))
}

private fun addCommand(
    editCommand: UIMoveByAxes?,
    programAndPointsVM: ProgramAndPointsVM,
    textAxes: MutableState<String>,
    distance: String,
    closeProgramMenu: () -> Unit
) {
    programAndPointsVM.addCommand(
        UIMoveByAxes(axes = Axes.valueOf(textAxes.value), distance = distance.toFloat())
    )

    closeProgramMenu()
}
