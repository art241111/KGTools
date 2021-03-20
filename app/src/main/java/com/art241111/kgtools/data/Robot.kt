package com.art241111.kgtools.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.github.art241111.tcpClient.connection.Status
import com.github.poluka.kControlLibrary.KRobot
import com.github.poluka.kControlLibrary.actions.move.MoveToPoint
import com.github.poluka.kControlLibrary.enity.position.Position
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class Robot {
    val robot = KRobot()
    // val moveInTime = MoveInTime(robot)
    var coordinate = robot.positionState

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    val programState = robot.programState

    var connect = mutableStateOf(false)
    var connectStatus = mutableStateOf(com.github.art241111.tcpClient.connection.Status.DISCONNECTED)

    fun moveToPoint(position: Position) {
        robot.run(MoveToPoint(position = position))
    }

    private var isRobotWasConnect = false
    private lateinit var jobConnectStatus: Job

    fun connect(
        ip: String,
        port: Int = 49152
    ) {
        if (connectStatus.value != Status.COMPLETED && connectStatus.value != Status.CONNECTING) {
            robot.connect(ip, port)

            if (!this::jobConnectStatus.isInitialized || !jobConnectStatus.isActive) {
                jobConnectStatus = scope.launch {
                    robot.statusState.collect { status ->
                        if (status == Status.COMPLETED) {
                            connect.value = true
                            isRobotWasConnect = true
                        } else {
                            if (isRobotWasConnect) {
                                isRobotWasConnect = false
                                jobConnectStatus.cancel()
                            }
                            connect.value = false
                        }

                        if (status != null) {
                            Log.d("new_status", status.toString())
                            connectStatus.value = status
                        }
                    }
                }
            }
        }
    }

    fun disconnect() {
        robot.disconnect()
    }
}
