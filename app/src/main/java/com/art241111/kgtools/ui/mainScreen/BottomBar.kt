package com.art241111.kgtools.ui.mainScreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.art241111.kcontrolsystem.ui.theme.red700
import com.art241111.kgtools.R
import com.art241111.kgtools.data.RobotVM
import com.art241111.kgtools.ui.navigation.MainNavigationVM

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
internal fun BottomBar(
    modifier: Modifier = Modifier,
    robotVM: RobotVM,
    navigate: MainNavigationVM,
    programAndPointsVM: ProgramAndPointsVM
) {
    val fabShape = CircleShape

    BottomAppBar(
        modifier = modifier,
        backgroundColor = Color.White,
        cutoutShape = fabShape,
        elevation = 10.dp
    ) {
        if (robotVM.connect.value) {
            IconButton(
                onClick = {
                    robotVM.disconnect()
                }
            ) {
                Icon(
                    modifier = Modifier.size(35.dp),
                    tint = Color.Green,
                    painter =
                    painterResource(id = R.drawable.ic_baseline_link_24),
                    contentDescription = null
                )
            }
        } else {

            IconButton(
                onClick = { navigate.moveConnect() }
            ) {
                Icon(
                    modifier = Modifier.size(35.dp),
                    tint = Color.Red,
                    painter =
                    painterResource(id = R.drawable.ic_baseline_link_off_48_red),
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.width(5.dp))

        val isRun = robotVM.isRun.collectAsState()

        if (!isRun.value) {
            IconButton(
                onClick = {
                    robotVM.runProgram(
                        UICommands = programAndPointsVM.getCommands().value!!,
                        points = programAndPointsVM.getPoints().value!!
                    )
                }
            ) {
                Icon(
                    modifier = Modifier.size(35.dp),
                    tint = Color.Green,
                    painter =
                    painterResource(id = R.drawable.ic_baseline_start_48_green),
                    contentDescription = null
                )
            }
        } else {
            IconButton(
                onClick = {
                }
            ) {
                Icon(
                    modifier = Modifier.size(35.dp),
                    tint = red700,
                    painter =
                    painterResource(id = R.drawable.ic_baseline_stop_24),
                    contentDescription = null
                )
            }
        }
    }
}
