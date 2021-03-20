package com.art241111.kgtools.data

import com.art241111.kcontrolsystem.data.Position
import com.github.poluka.kControlLibrary.enity.position.RPosition

class PositionImp : Position {
    private val position: RPosition = RPosition()

    override fun get(axes: Int): Double = position[axes]

    override fun set(axes: Int, value: Double) {
        position[axes] = value
    }
}
