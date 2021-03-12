package com.art241111.kcontrolsystem.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.art241111.joystickcomposeview.JoystickView
import com.art241111.kcontrolsystem.R
import com.art241111.kcontrolsystem.buttons.arrowButton.ArrowButton
import com.art241111.kcontrolsystem.data.Axes
import com.art241111.kcontrolsystem.data.MoveInTime
import com.art241111.kcontrolsystem.theme.red500

/**
 * Основной экран для управления роботом в пространстве
 */
@Composable
internal fun MoveXYZ(
    modifier: Modifier = Modifier,
    moveInTime: MoveInTime,
    isJoystick: Boolean = true,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            if (!isJoystick) {
                ButtonsXY(
                    moveInTime = moveInTime
                )
            } else {
                JoystickControl(moveInTime = moveInTime)
            }

            ButtonsZ(
                modifier = Modifier.padding(start = 40.dp),
                moveInTime = moveInTime
            )
        }
    }
}

@Composable
private fun JoystickControl(
    modifier: Modifier = Modifier,
    moveInTime: MoveInTime
) {
    val x = remember { mutableStateOf(0f) }
    val y = remember { mutableStateOf(0f) }

    JoystickView(
        modifier = modifier,
        x = x,
        y = y,
        backgroundColor = Color(248, 241, 235),
        joystickColor = red500
    )

    moveInTime[Axes.X] = x.value / 15.0
    moveInTime[Axes.Y] = -y.value / 15.0
}

@Composable
private fun ButtonsZ(
    modifier: Modifier = Modifier,
    moveInTime: MoveInTime
) {
    Column(modifier = modifier) {
        // Z up
        ArrowButton(
            onPressed = { moveInTime[Axes.Z] = moveInTime.defaultButtonDistanceLong },
            onReleased = { moveInTime[Axes.Z] = 0.0 }
        )

        Image(
            painter = painterResource(id = R.drawable.robot_z),
            modifier = Modifier
                .height(50.dp)
                .then(Modifier.width(50.dp))
                .then(Modifier.padding(top = 5.dp, bottom = 5.dp)),
            contentDescription = null
        )

        // Z down
        ArrowButton(
            onPressed = { moveInTime[Axes.Z] = -moveInTime.defaultButtonDistanceLong },
            onReleased = { moveInTime[Axes.Z] = 0.0 },
            degrees = 180f
        )
    }
}

/**
 * Прорисовка стрелок, для перемещения по осям XY
 */
@Composable
private fun ButtonsXY(
    modifier: Modifier = Modifier,
    moveInTime: MoveInTime
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Y up
        ArrowButton(
            onPressed = { moveInTime[Axes.Y] = moveInTime.defaultButtonDistanceLong },
            onReleased = { moveInTime[Axes.Y] = 0.0 }
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // X left
            ArrowButton(
                onPressed = { moveInTime[Axes.X] = -moveInTime.defaultButtonDistanceLong },
                onReleased = { moveInTime[Axes.X] = 0.0 },
                degrees = -90f
            )

            Image(
                painter = painterResource(id = R.drawable.robot_xy),
                modifier = Modifier
                    .height(70.dp)
                    .then(Modifier.width(70.dp))
                    .then(Modifier.padding(all = 5.dp)),
                contentDescription = null
            )

            // X right
            ArrowButton(
                onPressed = { moveInTime[Axes.X] = moveInTime.defaultButtonDistanceLong },
                onReleased = { moveInTime[Axes.X] = 0.0 },
                degrees = 90f
            )
        }

        // Y down
        ArrowButton(
            onPressed = { moveInTime[Axes.Y] = -moveInTime.defaultButtonDistanceLong },
            onReleased = { moveInTime[Axes.Y] = 0.0 },
            degrees = 180f
        )
    }
}
