package com.art241111.kgtools.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.art241111.kgtools.R
import com.github.poluka.kControlLibrary.enity.Axes
import com.github.poluka.kControlLibrary.enity.TypeOfMovement

interface UICommand {
    val programName: Int
    val image: Int

    @Composable
    fun toComposableString(): String
}

data class UIMoveToPoint(
    override val programName: Int = R.string.command_move_to_point,
    override val image: Int = R.drawable.move_to_point,
    var pointName: String,
    var type: TypeOfMovement
) : UICommand {

    @Composable
    override fun toComposableString(): String {
        return "${stringResource(id = programName)}: $pointName \n" +
            "${stringResource(id = R.string.command_move_to_point_type)}: $type"
    }

    override fun toString(): String {
        return "$command~$pointName~$type"
    }

    companion object Factory {
        const val command = "MOVE_TO_POINT"
    }
}

data class UIMoveByAxes(
    override val programName: Int = R.string.command_move_by_axes,
    override val image: Int = R.drawable.icon_move_by_coordinate,
    var axes: Axes,
    var distance: Float
) : UICommand {

    @Composable
    override fun toComposableString(): String {
        return "${stringResource(id = R.string.command_move_by)}: $axes \n" +
            "${stringResource(id = R.string.command_distance)}: $distance"
    }

    override fun toString(): String {
        return "$command~$axes~$distance"
    }

    companion object Factory {
        const val command = "MOVE_BY_AXES"
    }
}

data class UIOpenGripper(
    override val programName: Int = R.string.command_open_gripper,
    override val image: Int = R.drawable.icon_open_gripper
) : UICommand, Gripper(programName, image) {

    @Composable
    override fun toComposableString(): String {
        return stringResource(id = programName)
    }

    override fun toString(): String {
        return "$command~$commandOpen"
    }

    companion object Factory {
        const val commandOpen = "OPEN"
    }
}

data class UICloseGripper(
    override val programName: Int = R.string.command_close_gripper,
    override val image: Int = R.drawable.icon_close_gripper
) : UICommand, Gripper(programName, image) {
    @Composable
    override fun toComposableString(): String {
        return stringResource(id = programName)
    }

    override fun toString(): String {
        return "$command~$commandClose"
    }

    companion object Factory {
        const val commandClose = "CLOSE"
    }
}

abstract class Gripper(override val programName: Int, override val image: Int) : UICommand {
    companion object Factory {
        const val command = "GRIPPER"
    }

    @Composable
    abstract override fun toComposableString(): String
}

data class UIRepeat(
    val count: Int,
    override val programName: Int = R.string.command_repeat,
    override val image: Int = R.drawable.ic_baseline_repeat_24
) : UICommand {
    @Composable
    override fun toComposableString(): String {
        return "${stringResource(id = R.string.command_repeat)}: $count"
    }
}

data class UIMoveNearby(
    val pointName: String,
    val axes: Axes,
    val distance: Float,
    val angle: Float,
    override val programName: Int = R.string.command_nearby,
    override val image: Int = R.drawable.icon_move_by_coordinate
) : UICommand {

    @Composable
    override fun toComposableString(): String {
        return "${stringResource(id = R.string.command_nearby)}: $pointName \n" +
            "${stringResource(id = R.string.command_move_by)}: $axes \n" +
            "${stringResource(id = R.string.command_distance)}: $distance \n" +
            "${stringResource(id = R.string.command_angle)}: $angle"
    }

    override fun toString(): String {
        return "$command~$pointName~$axes~$distance~$angle"
    }

    companion object Factory {
        const val command = "MOVE_NEARBY"
    }
}

data class UIWaitSignal(
    val signal: Int,
    override val programName: Int = R.string.command_wait_signal,
    override val image: Int = R.drawable.ic_baseline_alarm_on_24
) : UICommand {

    @Composable
    override fun toComposableString(): String {
        return "${stringResource(id = R.string.command_wait_signal)}: $signal"
    }

    override fun toString(): String {
        return "$command~$signal"
    }

    companion object Factory {
        const val command = "WAIT_SIGNAL"
    }
}
