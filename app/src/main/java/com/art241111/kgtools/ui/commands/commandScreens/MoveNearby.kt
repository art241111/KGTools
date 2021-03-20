package com.art241111.kgtools.ui.commands.commandScreens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.art241111.kgtools.R
import com.art241111.kgtools.data.UIMoveNearby
import com.art241111.kgtools.ui.commands.ProgramNavigateVm
import com.art241111.kgtools.ui.mainScreen.ProgramAndPointsVM
import com.art241111.kgtools.ui.elements.Spinner
import com.github.poluka.kControlLibrary.enity.Axes

@Composable
internal fun ShowMoveNearby(
    programAndPointsVM: ProgramAndPointsVM,
    navigate: ProgramNavigateVm,
    editCommand: UIMoveNearby?,
    closeProgramMenu: () -> Unit
) {
    val points: List<String> = programAndPointsVM.getPointsName().value!!
    val errorPointNotCreated = stringResource(id = R.string.command_move_to_point_not_create)

    var defaultPoint = if (points.isNotEmpty()) points[0] else errorPointNotCreated
    val errorPointNotFound = stringResource(id = R.string.command_move_to_point_not_found)

    val axesValues = Axes.values()
    val axes = mutableListOf<String>()
    axesValues.forEach {
        axes.add(it.name)
    }

    var defaultAxes = if (axes.isNotEmpty()) axes[0] else ""
    var defaultDistance = "0.0"
    var defaultAngle = "0.0"

    if (editCommand != null) {
        defaultPoint =
            if (points.contains(editCommand.pointName)) editCommand.pointName else errorPointNotFound
        defaultAxes = editCommand.axes.toString()
        defaultDistance = editCommand.distance.toString()
        defaultAngle = editCommand.angle.toString()
    }

    val textPointsName = remember { mutableStateOf(defaultPoint) } // initial value
    Spinner(
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .padding(top = 40.dp),
        label = stringResource(id = R.string.command_move_to_point_choose_point),
        list = points,
        text = textPointsName
    )
    Spacer(modifier = Modifier.height(10.dp))

    val textAxes = remember { mutableStateOf(defaultAxes) } // initial value
    Spinner(
        modifier = Modifier.padding(horizontal = 40.dp),
        label = stringResource(id = R.string.command_move_by_axes_select_axes),
        list = axes,
        text = textAxes
    )

    var distance by remember { mutableStateOf(defaultDistance) } // initial value
    var angle by remember { mutableStateOf(defaultAngle) } // initial value

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
                    programAndPointsVM = programAndPointsVM,
                    textAxes = textAxes,
                    pointName = textPointsName,
                    distance = distance,
                    closeProgramMenu = closeProgramMenu,
                    angle = angle
                )
            }
        )
    )

    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        value = angle,
        onValueChange = { angle = it },
        label = { Text(text = stringResource(id = R.string.command_angle)) },
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
                    programAndPointsVM = programAndPointsVM,
                    textAxes = textAxes,
                    distance = distance,
                    angle = angle,
                    pointName = textPointsName,
                    closeProgramMenu = closeProgramMenu
                )
            }
        )
    )

    Spacer(modifier = Modifier.height(10.dp))
    val context = LocalContext.current
    val errorMessage = stringResource(id = R.string.command_move_to_point_create_point)
    Button(
        onClick = {
            when (textPointsName.value) {
                errorPointNotCreated -> {
                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
                errorPointNotFound -> {
                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    addCommand(
                        programAndPointsVM = programAndPointsVM,
                        textAxes = textAxes,
                        pointName = textPointsName,
                        distance = distance,
                        closeProgramMenu = closeProgramMenu,
                        angle = angle
                    )
                }
            }
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
}

private fun addCommand(
    programAndPointsVM: ProgramAndPointsVM,
    textAxes: MutableState<String>,
    pointName: MutableState<String>,
    distance: String,
    closeProgramMenu: () -> Unit,
    angle: String
) {
    programAndPointsVM.addCommand(
        UIMoveNearby(
            pointName = pointName.value,
            axes = Axes.valueOf(textAxes.value),
            distance = distance.toFloat(),
            angle = angle.toFloat(),
        )
    )

    closeProgramMenu()
}
