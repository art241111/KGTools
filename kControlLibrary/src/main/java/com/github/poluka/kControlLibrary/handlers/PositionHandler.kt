package com.github.poluka.kControlLibrary.handlers

import com.github.poluka.kControlLibrary.enity.position.Position
import com.github.poluka.kControlLibrary.enity.position.positionArrayFromString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PositionHandler(
    private val incomingText: StateFlow<String>
) {
    /**
     * State the position that you want to monitor
     * to obtain information about the position.
     */
    val positionState: MutableStateFlow<Position> = MutableStateFlow(Position())

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job) // Add handlers to Reader
    private lateinit var posJob: Job

    fun handlePosition() {
        posJob = scope.launch {
            if (this.isActive) {
                incomingText.collect { text ->
                    if (text.substringBefore(";").trim() == "POINT") {
                        val newPosition = positionParsing(text)
                        if (newPosition != null) {
                            positionState.value = newPosition
                        }
                    }
                }
            }
        }
    }

    fun stopHandlePosition() {
        if (this::posJob.isInitialized) {
            posJob.cancel()
        }
    }

    /**
     * Parse input data.
     * @param position - data to parse,
     * @return new position - when position change,
     * @return null - if old position.
     */
    private fun positionParsing(position: String): Position? {
        val newPosition = Position().positionArrayFromString(position)

        return if (newPosition != positionState.value) {
            newPosition
        } else {
            null
        }
    }
}
