package com.art241111.kcontrolsystem.ui.utils

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.art241111.kcontrolsystem.ui.data.Axes
import com.art241111.kcontrolsystem.ui.data.MoveInTime

class TiltControl(private val sensorManager: SensorManager) {
    lateinit var moveInTime: MoveInTime
    private var sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private lateinit var listener: SensorEventListener

    fun startTracking() {
        if (sensor != null) {
            listener = if (this::moveInTime.isInitialized) {
                MySensorEventListener(moveInTime)
            } else {
                MySensorEventListener(null)
            }

            sensorManager.registerListener(
                listener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        } else {
            Log.e("sensor", "Sensor not detected")
        }
    }

    fun stopTracking() {
        if (sensor != null && this::listener.isInitialized) {
            sensorManager.unregisterListener(listener, sensor)
            moveInTime[Axes.X] = 0.0
            moveInTime[Axes.Y] = 0.0
        } else {
            Log.e("sensor", "Sensor not detected")
        }
    }

    private class MySensorEventListener(val moveInTime: MoveInTime?) : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (moveInTime != null) {
                if (event != null) {
                    moveInTime[Axes.X] = -1 * event.values[0].toDouble()
                    moveInTime[Axes.Y] = -1 * event.values[1].toDouble()
                    Log.d(
                        "sensor",
                        "${-1 * event.values[0].toDouble()}: ${-1 * event.values[1].toDouble()}"
                    )
                }
            } else {
                Log.e("tilt_control_move", "[MoveInTime] is not initialized")
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Log.d("sensor", "$sensor: $accuracy")
        }
    }
}
