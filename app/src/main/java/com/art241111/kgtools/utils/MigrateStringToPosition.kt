package com.art241111.kgtools.utils

import com.github.poluka.kControlLibrary.enity.Axes
import com.github.poluka.kControlLibrary.enity.position.Point

/**
 * A class for converting strings to points
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

internal fun listToPosition(split: List<String>): Point {
    val returnPosition = Point()

    split.forEachIndexed { index, position ->
        returnPosition[Axes.values()[index]] = position.toDouble()
    }

    return returnPosition
}
