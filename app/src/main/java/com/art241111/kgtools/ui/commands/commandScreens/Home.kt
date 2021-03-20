package com.art241111.kgtools.ui.commands.commandScreens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.art241111.kgtools.R
import com.art241111.kgtools.data.UICloseGripper
import com.art241111.kgtools.data.UIOpenGripper
import com.art241111.kgtools.ui.commands.ProgramNavigateVm
import com.art241111.kgtools.ui.mainScreen.ProgramAndPointsVM

@Composable
internal fun ShowHome(
    programAndPointsVM: ProgramAndPointsVM,
    navigate: ProgramNavigateVm,
    closeProgramMenu: () -> Unit
) {
    if (programAndPointsVM.uiCommandUpgrade !is UICloseGripper && programAndPointsVM.uiCommandUpgrade !is UIOpenGripper) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.add_command),
            style = MaterialTheme.typography.h2
        )

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                navigate.moveByAxis()
            }
        ) {
            Text(text = stringResource(id = R.string.command_move_by_axes))
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                navigate.moveToPoint()
            }
        ) {
            Text(text = stringResource(id = R.string.command_move_to_point))
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                navigate.moveNearby()
            }
        ) {
            Text(text = stringResource(id = R.string.command_nearby))
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                navigate.moveWaitSignal()
            }
        ) {
            Text(text = stringResource(id = R.string.command_wait_signal))
        }
    }

    Spacer(modifier = Modifier.height(10.dp))
    Button(
        onClick = {
            programAndPointsVM.addCommand(UIOpenGripper())
            closeProgramMenu()
        }
    ) {
        Text(text = stringResource(id = R.string.command_open_gripper))
    }

    Spacer(modifier = Modifier.height(10.dp))
    Button(
        onClick = {
            programAndPointsVM.addCommand(UICloseGripper())
            closeProgramMenu()
        }
    ) {
        Text(text = stringResource(id = R.string.command_close_gripper))
    }

    Spacer(modifier = Modifier.height(10.dp))
    Button(
        onClick = {
            closeProgramMenu()
        }
    ) {
        Text(text = stringResource(id = R.string.cancel))
    }

    Spacer(modifier = Modifier.height(10.dp))
}
