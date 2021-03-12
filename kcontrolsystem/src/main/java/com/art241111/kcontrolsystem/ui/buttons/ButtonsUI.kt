package com.art241111.kcontrolsystem.ui.buttons

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.art241111.kcontrolsystem.ControlVM
import com.art241111.kcontrolsystem.R
import com.art241111.kcontrolsystem.ui.data.Axes
import com.art241111.kcontrolsystem.ui.data.MoveInTime
import com.art241111.kcontrolsystem.ui.data.Position
import com.art241111.kcontrolsystem.ui.data.UIMoveByCoordinate
import com.art241111.kcontrolsystem.ui.theme.TextHeader
import com.art241111.kcontrolsystem.ui.theme.red500
import com.art241111.kcontrolsystem.ui.utils.TiltControl

@Composable
private fun IconButtonWithState(
    modifier: Modifier = Modifier,
    text: String,
    iconId: Int,
    isActive: Boolean = false,
    activeColor: Color = Color.Red,
    defaultColor: Color = Color.Black,
    onClick: () -> Unit
) {
    val color = when (isActive) {
        true -> activeColor
        false -> defaultColor
    }

    Column(
        modifier = modifier
            .clickable { onClick() }
            .clip(RoundedCornerShape(5)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = iconId),
            contentDescription = text,
            tint = color,
        )

        Text(
            text = text,
            color = color
        )
    }
}

/**
 * @param tiltControl - требуется создать в MainActivity для правильной работы.
 * Также требуется перед запуском tiltControl установить [MoveInTime] для передачи
 * параметров движения на робота.
 */
@Composable
internal fun ButtonsView(
    modifier: Modifier = Modifier,
    coordinate: State<Position>,
    moveInTime: MoveInTime,
    moveByCoordinate: UIMoveByCoordinate,
    tiltControl: TiltControl,
    controlVM: ControlVM
) {

    controlVM.tiltControl = tiltControl
    controlVM.tiltControl.moveInTime = moveInTime

    val scrollState: ScrollState = rememberScrollState(0)

    Column(
        modifier = modifier
            .verticalScroll(
                scrollState,
                true,
                reverseScrolling = false
            )
            .fillMaxWidth()
            .then(Modifier.padding(top = 10.dp, bottom = 20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val state = remember { mutableStateOf(0) }

        Row(
            modifier = Modifier.padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButtonWithState(
                text = "Arrows",
                iconId = R.drawable.ic_baseline_joystick_24,
                isActive = state.value == 0,
                onClick = { state.value = 0 },
                activeColor = red500
            )

            Spacer(modifier = Modifier.width(10.dp))
            IconButtonWithState(
                text = "Joystick",
                iconId = R.drawable.ic_baseline_joystick_24,
                isActive = state.value == 1,
                onClick = { state.value = 1 },
                activeColor = red500
            )

            Spacer(modifier = Modifier.width(10.dp))
            IconButtonWithState(
                text = "Tint",
                iconId = R.drawable.ic_baseline_joystick_24,
                isActive = state.value == 2,
                onClick = { state.value = 2 },
                activeColor = red500
            )
        }

        if (state.value != 2) {
            controlVM.stopTrackingTilt()

            MoveXYZ(
                moveInTime = moveInTime,
                isJoystick = state.value == 1,
            )
        } else {
            controlVM.startTrackingTilt()
            TextHeader(
                text = R.string.control_tint_mode,
                color = red500
            )
        }

        SlowMoving(
            coordinate = coordinate,
            moveInTime = moveInTime,
            moveByCoordinate = moveByCoordinate,
            enabled = state.value != 2
        )
    }
}

@Composable
private fun SlowMoving(
    coordinate: State<Position>,
    moveInTime: MoveInTime,
    moveByCoordinate: UIMoveByCoordinate,
    enabled: Boolean = true
) {
    Spacer(Modifier.height(16.dp))
    TextButtons(
        editName = "X",
        value = coordinate.value[Axes.X],
        enabled = enabled,
        onDecreaseButtonClick = PressOrRelease(
            {
                moveInTime[Axes.X] = -moveInTime.defaultButtonDistanceShort
            },
            {
                moveInTime[Axes.X] = 0.0
            }
        ),
        onZoomButtonClick = PressOrRelease(
            {
                moveInTime[Axes.X] = moveInTime.defaultButtonDistanceShort
            },
            {
                moveInTime[Axes.X] = 0.0
            }
        )
    ) {
        if (it.isNotEmpty()) {
            moveByCoordinate.moveByX(it.toDouble())
        }
    }

    Spacer(Modifier.height(16.dp))
    TextButtons(
        editName = "Y",
        value = coordinate.value[Axes.Y],
        enabled = enabled,
        onDecreaseButtonClick = PressOrRelease(
            {
                moveInTime[Axes.Y] = -moveInTime.defaultButtonDistanceShort
            },
            {
                moveInTime[Axes.Y] = 0.0
            }
        ),
        onZoomButtonClick = PressOrRelease(
            {
                moveInTime[Axes.Y] = moveInTime.defaultButtonDistanceShort
            },
            {
                moveInTime[Axes.Y] = 0.0
            }
        )
    ) {
        if (it.isNotEmpty()) {
            moveByCoordinate.moveByY(it.toDouble())
        }
    }

    Spacer(Modifier.height(16.dp))

    TextButtons(
        editName = "Z",
        value = coordinate.value[Axes.Z],
        enabled = enabled,
        onDecreaseButtonClick = PressOrRelease(
            {
                moveInTime[Axes.Z] = -moveInTime.defaultButtonDistanceShort
            },
            {
                moveInTime[Axes.Z] = 0.0
            }
        ),
        onZoomButtonClick = PressOrRelease(
            {
                moveInTime[Axes.Z] = moveInTime.defaultButtonDistanceShort
            },
            {
                moveInTime[Axes.Z] = 0.0
            }
        )
    ) {
        if (it.isNotEmpty()) {
            moveByCoordinate.moveByZ(it.toDouble())
        }
    }
}

data class PressOrRelease(
    val onPressed: () -> Unit,
    val onReleased: () -> Unit,
)
