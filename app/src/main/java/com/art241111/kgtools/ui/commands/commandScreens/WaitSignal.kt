package com.art241111.kgtools.ui.commands.commandScreens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.art241111.kgtools.R
import com.art241111.kgtools.data.uiCommands.UIWaitSignal
import com.art241111.kgtools.ui.commands.ProgramNavigateVm
import com.art241111.kgtools.ui.mainScreen.ProgramAndPointsVM

/**
 * Screen for adding a wait signal command.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
@Composable
internal fun WaitSignal(
    programAndPointsVM: ProgramAndPointsVM,
    navigate: ProgramNavigateVm,
    editCommand: UIWaitSignal?,
    closeProgramMenu: () -> Unit
) {

    var defaultSignal = "0"
    if (editCommand != null) {
        defaultSignal = editCommand.signal.toString()
    }

    var signal by remember { mutableStateOf(defaultSignal) } // initial value
    CommandOutlinedTextField(
        value = signal,
        onValueChange = { signal = it },
        label = R.string.command_wait_signal,
        onDone = {
            addCommand(
                editCommand = editCommand,
                programAndPointsVM = programAndPointsVM,
                signal = signal,
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
                signal = signal,
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
    editCommand: UIWaitSignal?,
    programAndPointsVM: ProgramAndPointsVM,
    signal: String,
    closeProgramMenu: () -> Unit
) {
    programAndPointsVM.addCommand(
        UIWaitSignal(signal = signal.toInt())
    )

    closeProgramMenu()
}
