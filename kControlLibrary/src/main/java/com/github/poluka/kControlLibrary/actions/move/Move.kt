package com.github.poluka.kControlLibrary.actions.move

import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.Distance
import com.github.poluka.kControlLibrary.enity.position.Point

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class Move {
    companion object {
        infix fun to(point: Point) {
            MoveToPoint(point)
        }

        infix fun Program.to(point: Point) {
            add(MoveToPoint(point))
        }

        infix fun by(distance: Distance) {
            MoveOnDistance(distance)
        }

        infix fun Program.by(distance: Distance) {
            add(MoveOnDistance(distance))
        }
    }
}
