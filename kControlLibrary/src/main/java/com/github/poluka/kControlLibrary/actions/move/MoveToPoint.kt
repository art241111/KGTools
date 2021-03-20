package com.github.poluka.kControlLibrary.actions.move

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.TypeOfMovement
import com.github.poluka.kControlLibrary.enity.position.RPosition

private const val MOVE_TO_POINT = "MOVETO"

@ExecutedOnTheRobot
data class MoveToPoint(
    private val position: RPosition,
    private val typeOfMovement: TypeOfMovement = TypeOfMovement.LMOVE,
) : Command {
    override fun run() = "$MOVE_TO_POINT;$typeOfMovement;$position"
}

fun Program.moveToPoint(
    position: RPosition,
    typeOfMovement: TypeOfMovement = TypeOfMovement.LMOVE,
) = add(MoveToPoint(position, typeOfMovement))
