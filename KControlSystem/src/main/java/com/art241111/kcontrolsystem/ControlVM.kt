package com.art241111.kcontrolsystem

import androidx.lifecycle.ViewModel
import com.art241111.kcontrolsystem.utils.TiltControl

class ControlVM : ViewModel() {
    /**
     * Creating a tilt robot control
     */
    lateinit var tiltControl: TiltControl // Управление наклоном

    fun startTrackingTilt() {
        tiltControl.startTracking()
    }

    fun stopTrackingTilt() {
        tiltControl.stopTracking()
    }

    override fun onCleared() {
        tiltControl.stopTracking()
        super.onCleared()
    }
}
