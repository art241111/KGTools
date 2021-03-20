package com.github.poluka.kControlLibrary.actions.move

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.Axes
import com.github.poluka.kControlLibrary.enity.TypeOfMovement
import com.github.poluka.kControlLibrary.enity.position.RPosition

@ExecutedOnTheRobot
data class DepartPoint(
    val position: RPosition,
    val typeOfMovement: TypeOfMovement = TypeOfMovement.LMOVE,
    val angle: Double = 0.0,
    val dX: Double = 0.0,
    val dY: Double = 0.0,
    val dZ: Double = 0.0,
    val dO: Double = 0.0,
    val dA: Double = 0.0,
    val dT: Double = 0.0
) : Command {
    override fun run(): String {
        val newPosition = RPosition(
            x = position[Axes.X] + dX,
            y = position[Axes.Y] + dY,
            z = position[Axes.Z] + dZ,
            o = position[Axes.O] + dO,
            a = position[Axes.A] + dA,
            t = position[Axes.T] + dT + angle
        )
        return MoveToPoint(newPosition, typeOfMovement).run()
    }
}

fun Program.departPoint(
    position: RPosition,
    typeOfMovement: TypeOfMovement = TypeOfMovement.LMOVE,
    dX: Double = 0.0,
    dY: Double = 0.0,
    dZ: Double = 0.0,
    dO: Double = 0.0,
    dA: Double = 0.0,
    dT: Double = 0.0
) = add(DepartPoint(position, typeOfMovement, dX, dY, dZ, dO, dA, dT))
