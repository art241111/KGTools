package com.github.poluka.kControlLibrary.actions.move

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.position.RPosition

private const val MOVE_BY_C = "CMOVE"

@ExecutedOnTheRobot
class MoveC(
    private val positionOnArc: RPosition,
    private val endPosition: RPosition
) : Command {

    override fun run(): String = "$MOVE_BY_C;$positionOnArc;$endPosition"
}

fun Program.moveByArc(
    positionOnArc: RPosition,
    endPosition: RPosition
) = add(MoveC(positionOnArc, endPosition))
