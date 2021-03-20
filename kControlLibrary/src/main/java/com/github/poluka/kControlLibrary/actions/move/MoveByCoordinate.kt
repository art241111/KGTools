package com.github.poluka.kControlLibrary.actions.move

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.Axes

private const val MOVE_BY_COORDINATE = "MOVE;BASE"

/**
 * Moving a certain distance on one axis.
 * @param axes - coordinates that are used for moving,
 * @param distance - the distance to which the movement takes place.
 */
@ExecutedOnTheRobot
data class MoveByCoordinate(
    private val axes: Axes,
    private val distance: Double
) : Command {

    /**
     * Start program.
     */
    override fun run(): String {
        val arrayMoving = Array(6) { 0.0 }
        arrayMoving[axes.ordinal] = distance
        return "$MOVE_BY_COORDINATE;${arrayMoving.joinToString(separator = ";")}"
    }
//    "$MOVE_BY_COORDINATE;${(coordinate.ordinal + 1)};$distance"
}

fun Program.moveByCoordinate(axes: Axes, distance: Double) = add(MoveByCoordinate(axes, distance))
