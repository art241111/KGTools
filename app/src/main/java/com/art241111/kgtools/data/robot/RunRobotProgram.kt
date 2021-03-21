package com.art241111.kgtools.data.robot

import com.art241111.kgtools.data.uiCommands.UICommand
import com.github.poluka.kControlLibrary.KRobot
import com.github.poluka.kControlLibrary.actions.move.MoveToPoint
import com.github.poluka.kControlLibrary.dsl.kProgram
import com.github.poluka.kControlLibrary.enity.position.Position
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class RunRobotProgram(private val robot: KRobot) {
    val programState = robot.programState

    fun moveToPoint(position: Position) {
        robot.run(MoveToPoint(position = position))
    }

    /**
     *  Run UI program on robot
     */
    private val _isRun = MutableStateFlow(false)
    val isRun: StateFlow<Boolean> = _isRun
    fun runProgram(
        UICommands: List<UICommand>,
        points: MutableMap<String, Position>
    ) {
        _isRun.value = true

        robot.run(
            kProgram {
                UICommands.forEach {
                    val command = it.getCommand(points)
                    command?.let { it1 -> this.add(it1) }
                }
            }
        )

        _isRun.value = false
    }
}
