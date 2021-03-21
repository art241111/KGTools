package com.art241111.kgtools.data.uiCommands

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.art241111.kgtools.R
import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.move.MoveToPoint
import com.github.poluka.kControlLibrary.enity.TypeOfMovement
import com.github.poluka.kControlLibrary.enity.position.Position

/**
 * Command to move to a point.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class UIMoveToPoint(
    var pointName: String,
    var type: TypeOfMovement,
) : UICommand {

    @Composable
    override fun toComposableString(): String {
        return "${stringResource(id = R.string.command_move_to_point)}: $pointName \n" +
            "${stringResource(id = R.string.command_move_to_point_type)}: $type"
    }

    override fun getCommand(points: MutableMap<String, Position>): Command? {
        return points[pointName]?.let { MoveToPoint(it, type) }
    }

    override fun getImage(): Int = R.drawable.move_to_point
    override fun getProgramName(): Int = R.string.command_move_to_point

    override fun toString(): String {
        return "$command~$pointName~$type"
    }

    companion object Factory {
        const val command = "MOVE_TO_POINT"
    }
}
