package com.art241111.kcontrolsystem.data

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

const val DELAY_SEND_SP = "DELAY_SEND"
const val LONG_MOVE_SP = "LONG_MOVE"
const val SHORT_MOVE_SP = "SHORT_MOVE"

class MoveInTime(
    private val move: (x: Double, y: Double, z: Double, o: Double, a: Double, t: Double) -> Unit
) {
    /**
     * Задержка отправки сообщения
     */
    var delaySending = 70L
    var defaultButtonDistanceLong = 10.0
    var defaultButtonDistanceShort = 1.0

    private var isMoving = false

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private var moveDistance = Array(6) { 0.0 }

    /**
     * Получаем доступ через координаты.
     * @param axes - координата, по которой нужно олучить значения.
     */
    operator fun get(axes: Axes) =
        moveDistance[axes.ordinal]

    /**
     * Изменяем значения через координаты.
     * @param axes - координата, по которой нужно изменить значения,
     * @param value - значение, которое нужно установить.
     */
    operator fun set(axes: Axes, value: Double) {

        if (value == -0.0) moveDistance[axes.ordinal] = 0.0
        else moveDistance[axes.ordinal] = value

        if (moveDistance.contentEquals(Array(6) { 0.0 })) {
            isMoving = false
            stopMoving()
        } else {
            if (!isMoving) {
                isMoving = true
                startMoving()
            }
        }
    }

    /**
     * Старт перемещения по тем координатам, что заданы в массиви moveDistance
     */
    private lateinit var jobMoving: Job

    private fun startMoving() {
        if (!this::jobMoving.isInitialized || !jobMoving.isActive) {
            jobMoving = scope.launch {
                while (isMoving && this.isActive) {
                    if (!moveDistance.contentEquals(Array(6) { 0.0 })) {
                        move(
                            moveDistance[0],
                            moveDistance[1],
                            moveDistance[2],
                            moveDistance[3],
                            moveDistance[4],
                            moveDistance[5]
                        )
                    } else {
                        isMoving = false
                        scope.cancel()
                        jobMoving.cancel()
                        this.cancel()
                    }
                    Log.d("Job", jobMoving.children.joinToString())
                    delay(delaySending)
                }
            }
        }
    }

    /**
     * Остановка цикла отправки и обнуление массива перемещений
     */
    fun stopMoving() {
        isMoving = false
        moveDistance = Array(6) { 0.0 }

        if (this::jobMoving.isInitialized) jobMoving.cancel()
    }
}
