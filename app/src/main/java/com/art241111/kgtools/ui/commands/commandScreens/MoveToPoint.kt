package com.art241111.kgtools.ui.commands.commandScreens

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.art241111.kgtools.R
import com.art241111.kgtools.data.uiCommands.UIMoveToPoint
import com.art241111.kgtools.ui.commands.ProgramNavigateVm
import com.art241111.kgtools.ui.mainScreen.ProgramAndPointsVM
import com.art241111.kgtools.ui.views.Spinner
import com.github.poluka.kControlLibrary.enity.TypeOfMovement

@Composable
internal fun ShowMoveToPoint(
    programAndPointsVM: ProgramAndPointsVM,
    navigate: ProgramNavigateVm,
    editCommand: UIMoveToPoint?,
    closeProgramMenu: () -> Unit
) {
    val points: List<String> = programAndPointsVM.getPointsName().value!!
    val errorPointNotCreated = stringResource(id = R.string.command_move_to_point_not_create)

    val typesOfMovement: List<String> = TypeOfMovement.values().map { it.name }

    var defaultPoint = if (points.isNotEmpty()) points[0] else errorPointNotCreated
    var defaultTypeOfMovement = if (typesOfMovement.isNotEmpty()) typesOfMovement[0] else ""

    val errorPointNotFound = stringResource(id = R.string.command_move_to_point_not_found)
    if (editCommand != null) {
        defaultPoint =
            if (points.contains(editCommand.pointName)) editCommand.pointName else errorPointNotFound
        defaultTypeOfMovement = editCommand.type.toString()
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

    val textTypes = remember { mutableStateOf(defaultTypeOfMovement) } // initial value
    Spinner(
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .padding(top = 40.dp),
        label = stringResource(id = R.string.command_move_to_point_type),
        list = typesOfMovement,
        text = textTypes
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
                    programAndPointsVM.addCommand(
                        UIMoveToPoint(
                            pointName = textPointsName.value,
                            type = TypeOfMovement.valueOf(textTypes.value)
                        )
                    )

                    closeProgramMenu()
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
