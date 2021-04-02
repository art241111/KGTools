package com.art241111.kgtools.ui.commands.commandScreens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.art241111.kgtools.R
import com.art241111.kgtools.data.uiCommands.UIMoveByAxes
import com.art241111.kgtools.ui.commands.ProgramNavigateVm
import com.art241111.kgtools.ui.mainScreen.ProgramAndPointsVM
import com.art241111.kgtools.ui.views.Spinner
import com.github.poluka.kControlLibrary.enity.Axes

/**
 * Screen for adding a move by  axes command.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
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

    CommandOutlinedTextField(
        value = distance,
        onValueChange = { distance = it },
        label = R.string.command_distance,
        onDone = {
            addCommand(
                editCommand = editCommand,
                programAndPointsVM = programAndPointsVM,
                textAxes = textAxes,
                distance = distance,
                closeProgramMenu = closeProgramMenu
            )
        }
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
