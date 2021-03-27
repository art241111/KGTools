package com.art241111.kgtools.data.uiCommands

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.art241111.kgtools.R
import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.move.MoveByAxes
import com.github.poluka.kControlLibrary.enity.Axes
import com.github.poluka.kControlLibrary.enity.position.Point

/**
 * Command for moving along axes.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class UIMoveByAxes(
    var axes: Axes,
    var distance: Float,
) : UICommand {

    @Composable
    override fun toComposableString(): String {
        return "${stringResource(id = R.string.command_move_by)}: $axes \n" +
            "${stringResource(id = R.string.command_distance)}: $distance"
    }

    override fun getCommand(points: MutableMap<String, Point>): Command {
        return MoveByAxes(axes, distance.toDouble())
    }

    override fun getImage(): Int = R.drawable.icon_move_by_coordinate
    override fun getProgramName(): Int = R.string.command_move_by_axes

    override fun toString(): String {
        return "$command~$axes~$distance"
    }

    companion object Factory {
        const val command = "MOVE_BY_AXES"
    }
}
