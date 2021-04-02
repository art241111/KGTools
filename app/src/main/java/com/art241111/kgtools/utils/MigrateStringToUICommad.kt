package com.art241111.kgtools.utils

import com.art241111.kgtools.data.uiCommands.Gripper
import com.art241111.kgtools.data.uiCommands.UICloseGripper
import com.art241111.kgtools.data.uiCommands.UICommand
import com.art241111.kgtools.data.uiCommands.UIMoveByAxes
import com.art241111.kgtools.data.uiCommands.UIMoveNearby
import com.art241111.kgtools.data.uiCommands.UIMoveToPoint
import com.art241111.kgtools.data.uiCommands.UIOpenGripper
import com.art241111.kgtools.data.uiCommands.UIWaitSignal
import com.github.poluka.kControlLibrary.enity.Axes
import com.github.poluka.kControlLibrary.enity.TypeOfMovement

/**
 * A class for converting strings to commands
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

internal fun getCommandFromString(command: String): UICommand? {
    return when (command.split("~")[0]) {
        UIMoveToPoint.command -> {
            getUIMoveToPoint(command)
        }
        UIMoveByAxes.command -> {
            getUIMoveByAxes(command)
        }
        Gripper.command -> {
            getGripper(command)
        }
        UIMoveNearby.command -> {
            getUIMoveNearby(command)
        }
        UIWaitSignal.command -> {
            getUIWaitSignal(command)
        }
        else -> {
            null
        }
    }
}

private fun getUIWaitSignal(command: String): UIWaitSignal? {
    val commandDetails = command.split("~")
    return UIWaitSignal(signal = commandDetails[1].toInt())
}

private fun getGripper(command: String): Gripper {
    val commandDetails = command.split("~")
    return if (commandDetails[1] == UIOpenGripper.commandOpen) {
        UIOpenGripper()
    } else {
        UICloseGripper()
    }
}

private fun getUIMoveByAxes(command: String): UIMoveByAxes {
    val commandDetails = command.split("~")
    return UIMoveByAxes(
        axes = Axes.valueOf(commandDetails[1]),
        distance = commandDetails[2].toFloat()
    )
}

private fun getUIMoveToPoint(command: String): UIMoveToPoint {
    val commandDetails = command.split("~")
    return UIMoveToPoint(
        pointName = commandDetails[1],
        type = TypeOfMovement.valueOf(commandDetails[2])
    )
}

private fun getUIMoveNearby(command: String): UIMoveNearby {
    val commandDetails = command.split("~")
    return UIMoveNearby(
        pointName = commandDetails[1],
        axes = Axes.valueOf(commandDetails[2]),
        distance = commandDetails[3].toFloat(),
        angle = commandDetails[4].toFloat(),
    )
}