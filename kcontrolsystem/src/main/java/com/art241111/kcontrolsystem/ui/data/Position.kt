package com.art241111.kcontrolsystem.ui.data

/**
 * Массив, который хранит позицию робота.
 * @author artem241120@gmail.com
 */
class Position(
    x: Double = 0.0,
    y: Double = 0.0,
    z: Double = 0.0,
    o: Double = 0.0,
    a: Double = 0.0,
    t: Double = 0.0
) {
    private val position = doubleArrayOf(x, y, z, o, a, t)

    constructor(x: Int, y: Int = 0, z: Int = 0, o: Int = 0, a: Int = 0, t: Int = 0) : this(
        x.toDouble(),
        y.toDouble(),
        z.toDouble(),
        o.toDouble(),
        a.toDouble(),
        t.toDouble()
    )

    /**
     *
     */
    override fun toString(): String {
        return position.joinToString(separator = ";")
    }

    /**
     * Получаем доступ через координаты.
     * @param axes - координата, по которой нужно олучить значения.
     */
    operator fun get(axes: Axes) =
        position[axes.ordinal]

    /**
     * Изменяем значения через координаты.
     * @param axes - координата, по которой нужно изменить значения,
     * @param value - значение, которое нужно установить.
     */
    operator fun set(axes: Axes, value: Double) {
        position[axes.ordinal] = value
    }
}
