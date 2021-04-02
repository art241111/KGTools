package com.art241111.kgtools.data.uiCommands

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.art241111.kgtools.R
import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.move.DepartPoint
import com.github.poluka.kControlLibrary.enity.Axes
import com.github.poluka.kControlLibrary.enity.TypeOfMovement
import com.github.poluka.kControlLibrary.enity.position.Point

/**
 * Command to move to a point at a distance and at an angle of gripper.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class UIMoveNearby(
    val pointName: String,
    val axes: Axes,
    val distance: Float,
    val angle: Float,
) : UICommand {

    @Composable
    override fun toComposableString(): String {
        return "${stringResource(id = R.string.command_nearby)}: $pointName \n" +
            "${stringResource(id = R.string.command_move_by)}: $axes \n" +
            "${stringResource(id = R.string.command_distance)}: $distance \n" +
            "${stringResource(id = R.string.command_angle)}: $angle"
    }

    override fun getCommand(points: MutableMap<String, Point>): Command? {
        return points[pointName]?.let {
            return when (axes) {
                Axes.X -> {
                    points[pointName]?.let {
                        DepartPoint(
                            point = it,
                            typeOfMovement = TypeOfMovement.LMOVE,
                            dX = distance.toDouble(),
                            angle = angle.toDouble()
                        )
                    }
                }
                Axes.Y -> {
                    points[pointName]?.let {
                        DepartPoint(
                            point = it,
                            typeOfMovement = TypeOfMovement.LMOVE,
                            dY = distance.toDouble(),
                            angle = angle.toDouble()
                        )
                    }
                }
                Axes.Z -> {
                    points[pointName]?.let {
                        DepartPoint(
                            point = it,
                            typeOfMovement = TypeOfMovement.LMOVE,
                            dZ = distance.toDouble(),
                            angle = angle.toDouble()
                        )
                    }
                }
                Axes.O -> {
                    points[pointName]?.let {
                        DepartPoint(
                            point = it,
                            typeOfMovement = TypeOfMovement.LMOVE,
                            dO = distance.toDouble(),
                            angle = angle.toDouble()
                        )
                    }
                }
                Axes.A -> {
                    points[pointName]?.let {
                        DepartPoint(
                            point = it,
                            typeOfMovement = TypeOfMovement.LMOVE,
                            dA = distance.toDouble(),
                            angle = angle.toDouble()
                        )
                    }
                }
                Axes.T -> {
                    points[pointName]?.let {
                        DepartPoint(
                            point = it,
                            typeOfMovement = TypeOfMovement.LMOVE,
                            dT = distance.toDouble(),
                            angle = angle.toDouble()
                        )
                    }
                }
            }
        }
    }

    override fun getImage(): Int = R.drawable.icon_move_by_coordinate
    override fun getProgramName(): Int = R.string.command_nearby

    override fun toString(): String {
        return "$command~$pointName~$axes~$distance~$angle"
    }

    companion object Factory {
        const val command = "MOVE_NEARBY"
    }
}
