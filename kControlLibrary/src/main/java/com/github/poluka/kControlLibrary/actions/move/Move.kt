package com.github.poluka.kControlLibrary.actions.move

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.dsl.Program

private const val MOVE_BY_COORDINATE = "MOVE;BASE"

/**
 * Moving a certain distance on one axis.
 * @param x,y,z,o,a,t - coordinates that are used for moving,
 */
@ExecutedOnTheRobot
data class Move(
    private val x: Double = 0.0,
    private val y: Double = 0.0,
    private val z: Double = 0.0,
    private val o: Double = 0.0,
    private val a: Double = 0.0,
    private val t: Double = 0.0
) : Command {

    /**
     * Start program.
     */
    override fun run(): String {
        return "$MOVE_BY_COORDINATE;$x;$y;$z;$o;$a;$t;"
    }
}

fun Program.move(
    x: Double = 0.0,
    y: Double = 0.0,
    z: Double = 0.0,
    o: Double = 0.0,
    a: Double = 0.0,
    t: Double = 0.0
) = add(Move(x, y, z, o, a, t))
