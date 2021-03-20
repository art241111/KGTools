package com.art241111.kcontrolsystem.data

/**
 * Массив, который хранит позицию робота.
 * @author artem241120@gmail.com
 */
interface Position {
    /**
     * Получаем доступ через координаты.
     * @param axes - координата, по которой нужно получить значения.
     */
    operator fun get(axes: Int): Double

    /**
     * Изменяем значения через координаты.
     * @param axes - координата, по которой нужно изменить значения,
     * @param value - значение, которое нужно установить.
     */
    operator fun set(axes: Int, value: Double)
}
