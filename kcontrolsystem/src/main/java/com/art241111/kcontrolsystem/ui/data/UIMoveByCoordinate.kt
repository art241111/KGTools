package com.art241111.kcontrolsystem.ui.data

import androidx.compose.runtime.State

interface UIMoveByCoordinate {
    fun moveByX(point: Double)
    fun moveByY(point: Double)
    fun moveByZ(point: Double)
    fun moveByO(point: Double)
    fun moveByA(point: Double)
    fun moveByT(point: Double)
}

class UIMoveByCoordinateKRobot(
    private val positionState: State<Position>,
    private val move: (position: Position) -> Unit
) : UIMoveByCoordinate {
    override fun moveByX(point: Double) {
        moveByCoordinate(Axes.X, point)
    }

    override fun moveByY(point: Double) {
        moveByCoordinate(Axes.Y, point)
    }

    override fun moveByZ(point: Double) {
        moveByCoordinate(Axes.Z, point)
    }

    override fun moveByO(point: Double) {
        moveByCoordinate(Axes.O, point)
    }

    override fun moveByA(point: Double) {
        moveByCoordinate(Axes.A, point)
    }

    override fun moveByT(point: Double) {
        moveByCoordinate(Axes.T, point)
    }

    private fun moveByCoordinate(axes: Axes, point: Double) {
        val position = positionState.value
        position[axes] = point

        move(position)
    }
}
