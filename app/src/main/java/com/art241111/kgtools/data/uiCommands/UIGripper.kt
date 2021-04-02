package com.art241111.kgtools.data.uiCommands

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.art241111.kgtools.R
import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.gripper.CloseGripper
import com.github.poluka.kControlLibrary.actions.gripper.OpenGripper
import com.github.poluka.kControlLibrary.enity.position.Point

/**
 * Commands for gripper.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class UIOpenGripper() : UICommand, Gripper() {

    @Composable
    override fun toComposableString(): String {
        return stringResource(id = R.string.command_open_gripper)
    }

    override fun getCommand(points: MutableMap<String, Point>): Command {
        return OpenGripper()
    }

    override fun getImage(): Int = R.drawable.icon_open_gripper

    override fun getProgramName(): Int = R.string.command_open_gripper

    override fun toString(): String {
        return "$command~$commandOpen"
    }

    companion object Factory {
        const val commandOpen = "OPEN"
    }
}

class UICloseGripper() : UICommand, Gripper() {
    @Composable
    override fun toComposableString(): String {
        return stringResource(id = R.string.command_close_gripper)
    }

    override fun getCommand(points: MutableMap<String, Point>): Command {
        return CloseGripper()
    }

    override fun getImage(): Int = R.drawable.icon_close_gripper
    override fun getProgramName(): Int = R.string.command_close_gripper

    override fun toString(): String {
        return "$command~$commandClose"
    }

    companion object Factory {
        const val commandClose = "CLOSE"
    }
}

abstract class Gripper : UICommand {
    companion object Factory {
        const val command = "GRIPPER"
    }

    @Composable
    abstract override fun toComposableString(): String
}
