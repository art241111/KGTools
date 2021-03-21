package com.art241111.kgtools.data.uiCommands

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.art241111.kgtools.R
import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.delay.WaitingSignal
import com.github.poluka.kControlLibrary.enity.position.Position

/**
 *
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class UIWaitSignal(
    val signal: Int,
) : UICommand {

    @Composable
    override fun toComposableString(): String {
        return "${stringResource(id = R.string.command_wait_signal)}: $signal"
    }

    override fun getCommand(points: MutableMap<String, Position>): Command {
        return WaitingSignal(signal)
    }

    override fun getImage(): Int = R.drawable.ic_baseline_alarm_on_24
    override fun getProgramName(): Int = R.string.command_wait_signal

    override fun toString(): String {
        return "$command~$signal"
    }

    companion object Factory {
        const val command = "WAIT_SIGNAL"
    }
}
