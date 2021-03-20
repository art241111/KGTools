package com.art241111.kgtools.utils

import com.art241111.kgtools.data.UICloseGripper
import com.art241111.kgtools.data.UICommand
import com.art241111.kgtools.data.UIMoveByAxes
import com.art241111.kgtools.data.UIMoveNearby
import com.art241111.kgtools.data.UIMoveToPoint
import com.art241111.kgtools.data.UIOpenGripper
import com.github.poluka.kControlLibrary.KRobot
import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.gripper.CloseGripper
import com.github.poluka.kControlLibrary.actions.gripper.OpenGripper
import com.github.poluka.kControlLibrary.actions.move.DepartPoint
import com.github.poluka.kControlLibrary.actions.move.MoveByCoordinate
import com.github.poluka.kControlLibrary.actions.move.MoveToPoint
import com.github.poluka.kControlLibrary.dsl.kProgram
import com.github.poluka.kControlLibrary.enity.Axes
import com.github.poluka.kControlLibrary.enity.TypeOfMovement
import com.github.poluka.kControlLibrary.enity.position.Position
import kotlinx.coroutines.flow.MutableStateFlow

class RunProgram() {

    val isRun = MutableStateFlow(false)

    internal fun runProgram(
        robot: KRobot,
        UICommands: List<UICommand>,
        points: MutableMap<String, Position>
    ) {
        isRun.value = true

        robot.run(
            kProgram {
                UICommands.forEach {
                    val command = migrateUICommandToKCommand(it, points)
                    command?.let { it1 -> this.add(it1) }
                }
            }
        )

        isRun.value = false
    }

    private fun migrateUICommandToKCommand(
        command: UICommand,
        points: MutableMap<String, Position>
    ): Command? {
        return when (command) {
            is UIMoveToPoint -> points[command.pointName]?.let { MoveToPoint(it, command.type) }
            is UIMoveByAxes -> MoveByCoordinate(command.axes, command.distance.toDouble())
            is UIOpenGripper -> OpenGripper()
            is UICloseGripper -> CloseGripper()
            is UIMoveNearby -> {
                doUIMoveNearby(command, points)
            }
            else -> null
        }
    }

    private fun doUIMoveNearby(
        command: UIMoveNearby,
        points: MutableMap<String, Position>
    ): DepartPoint? {
        return when (command.axes) {
            Axes.X -> {
                points[command.pointName]?.let {
                    DepartPoint(
                        position = it,
                        typeOfMovement = TypeOfMovement.LMOVE,
                        dX = command.distance.toDouble(),
                        angle = command.angle.toDouble()
                    )
                }
            }
            Axes.Y -> {
                points[command.pointName]?.let {
                    DepartPoint(
                        position = it,
                        typeOfMovement = TypeOfMovement.LMOVE,
                        dY = command.distance.toDouble(),
                        angle = command.angle.toDouble()
                    )
                }
            }
            Axes.Z -> {
                points[command.pointName]?.let {
                    DepartPoint(
                        position = it,
                        typeOfMovement = TypeOfMovement.LMOVE,
                        dZ = command.distance.toDouble(),
                        angle = command.angle.toDouble()
                    )
                }
            }
            Axes.O -> {
                points[command.pointName]?.let {
                    DepartPoint(
                        position = it,
                        typeOfMovement = TypeOfMovement.LMOVE,
                        dO = command.distance.toDouble(),
                        angle = command.angle.toDouble()
                    )
                }
            }
            Axes.A -> {
                points[command.pointName]?.let {
                    DepartPoint(
                        position = it,
                        typeOfMovement = TypeOfMovement.LMOVE,
                        dA = command.distance.toDouble(),
                        angle = command.angle.toDouble()
                    )
                }
            }
            Axes.T -> {
                points[command.pointName]?.let {
                    DepartPoint(
                        position = it,
                        typeOfMovement = TypeOfMovement.LMOVE,
                        dT = command.distance.toDouble(),
                        angle = command.angle.toDouble()
                    )
                }
            }
        }
    }
}
