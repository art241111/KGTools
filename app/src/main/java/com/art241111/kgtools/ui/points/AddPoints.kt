package com.art241111.kgtools.ui.points

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art241111.kcontrolsystem.ControlView
import com.art241111.kcontrolsystem.data.ControlVM
import com.art241111.kcontrolsystem.data.MoveInTime
import com.art241111.kcontrolsystem.data.UIMoveByCoordinateKRobot
import com.art241111.kcontrolsystem.ui.utils.TiltControl
import com.art241111.kgtools.R
import com.art241111.kgtools.ui.RobotVM
import com.art241111.kgtools.ui.elements.DefaultScreenBody
import com.art241111.kgtools.ui.mainScreen.ProgramAndPointsVM
import com.github.poluka.kControlLibrary.actions.move.Move
import com.github.poluka.kControlLibrary.enity.position.Position

@Composable
fun AddPoint(
    modifier: Modifier = Modifier,
    pointVM: ProgramAndPointsVM,
    navigateBack: () -> Unit,
    robotVM: RobotVM,
    tiltControl: TiltControl,
) {
    val controlVM = viewModel<ControlVM>()

    val coordinate = robotVM.coordinate.collectAsState()
    var pointName by remember { mutableStateOf("") }
    var pointIndex by remember { mutableStateOf(-1) }

    if (pointVM.pointNameUpgrade != null) {
        pointIndex = pointVM.pointNameUpgrade!!
        pointName = pointVM.getPointsName().value!![pointIndex]
        robotVM.robot.moveToPoint(pointVM.getPoints().value?.get(pointName)!!)

        pointVM.pointNameUpgrade = null
    }

    val errorNameTakes = stringResource(id = R.string.point_name_take)
    val context = LocalContext.current

    DefaultScreenBody(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp)
                        .wrapContentHeight(),
                    value = pointName,
                    onValueChange = {
                        pointName = it
                    },
                    label = { Text(text = stringResource(id = R.string.point_name)) },
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            defaultKeyboardAction(ImeAction.Done)
                            addPoint(
                                pointsVM = pointVM,
                                name = pointName,
                                coordinates = coordinate.value,
                                context = context,
                                nameTakeErrorText = errorNameTakes,
                                navigateBack = {
                                    controlVM.stopTrackingTilt()
                                    navigateBack()
                                },
                                pointIndex = pointIndex
                            )
                        }
                    )

                )
                Spacer(modifier = Modifier.height(5.dp))

                Button(
                    onClick = {
                        addPoint(
                            pointsVM = pointVM,
                            name = pointName,
                            coordinates = coordinate.value,
                            context = context,
                            nameTakeErrorText = errorNameTakes,
                            navigateBack = {
                                controlVM.stopTrackingTilt()
                                navigateBack()
                            },
                            pointIndex = pointIndex
                        )
                    }
                ) {
                    Text(text = stringResource(id = R.string.point_add))
                }
                Spacer(modifier = Modifier.height(5.dp))

                Button(
                    onClick = {
                        controlVM.stopTrackingTilt()
                        navigateBack()
                    }
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            ControlView(
                coordinate = coordinate,
                moveInTime = MoveInTime(
                    delaySending = robotVM.delaySending,
                    defaultButtonDistanceLong = robotVM.defaultButtonDistanceLong,
                    defaultButtonDistanceShort = robotVM.defaultButtonDistanceShort,
                    move = { x, y, z, o, a, t ->
                        robotVM.robot.robot.dangerousRun(Move(x, y, z, o, a, t))
                    }
                ),
                moveByCoordinate = UIMoveByCoordinateKRobot(coordinate) {},
                tiltControl = tiltControl,
                controlVM = controlVM
            )
        }
    }
}

private fun addPoint(
    pointsVM: ProgramAndPointsVM,
    name: String,
    coordinates: Position,
    context: Context,
    nameTakeErrorText: String,
    navigateBack: () -> Unit,
    pointIndex: Int
) {
    if (pointIndex == -1) {
        if (pointsVM.addPoint(name, coordinates)) {
            navigateBack()
        } else {
            Toast.makeText(
                context,
                nameTakeErrorText,
                Toast.LENGTH_LONG
            ).show()
        }
    } else {
        pointsVM.replacePoint(
            index = pointIndex,
            newName = name,
            newCoordinate = coordinates
        )
    }
}
