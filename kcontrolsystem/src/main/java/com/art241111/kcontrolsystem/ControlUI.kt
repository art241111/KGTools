package com.art241111.kcontrolsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.art241111.kcontrolsystem.ui.buttons.ButtonsView
import com.art241111.kcontrolsystem.ui.data.MoveInTime
import com.art241111.kcontrolsystem.ui.data.Position
import com.art241111.kcontrolsystem.ui.data.UIMoveByCoordinate
import com.art241111.kcontrolsystem.ui.utils.TiltControl

@Composable
fun ControlView(
    modifier: Modifier = Modifier,
    coordinate: State<Position>,
    moveInTime: MoveInTime,
    moveByCoordinate: UIMoveByCoordinate,
    tiltControl: TiltControl,
    controlVM: ControlVM
) {
    Surface(
        shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10),
        modifier = modifier,
        elevation = 20.dp
    ) {
        ButtonsView(
            coordinate = coordinate,
            moveInTime = moveInTime,
            moveByCoordinate = moveByCoordinate,
            tiltControl = tiltControl,
            controlVM = controlVM
        )
    }
}
