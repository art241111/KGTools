package com.art241111.kgtools.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.art241111.kgtools.data.*
import com.github.poluka.kControlLibrary.enity.Axes
import com.github.poluka.kControlLibrary.enity.TypeOfMovement
import com.github.poluka.kControlLibrary.enity.position.Position
import java.io.*

private const val FILENAME = "kGT"

class UIProgramUtils {
    fun saveProgram(
        context: Context,
        UICommands: List<UICommand>,
        points: Map<String, Position>
    ) {
        writeFile(
            fileText = createFile(points, UICommands),
            context = context
        )
    }

    private fun createFile(
        points: Map<String, Position>,
        UICommands: List<UICommand>,
    ): String {
        var outText = "POINTS \n"

        for ((name, coordinate) in points) {
            outText += "$name=$coordinate\n"
        }
        outText += "PROGRAM \n"
        outText += UICommands.joinToString(separator = "\n") + "\n"

        return outText
    }

    fun loadProgram(context: Context): Pair<MutableMap<String, Position>, List<UICommand>> =
        readFile(context)

    private fun writeFile(
        fileText: String,
        context: Context
    ) {

        try {
            // отрываем поток для записи
            val bw = BufferedWriter(
                OutputStreamWriter(
                    context.openFileOutput(FILENAME, MODE_PRIVATE)
                )
            )
            // пишем данные
            bw.write(fileText)
            // закрываем поток
            bw.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun readFile(
        context: Context
    ): Pair<MutableMap<String, Position>, List<UICommand>> {
        var str: String?
        val points = mutableMapOf<String, Position>()
        val commands = mutableListOf<UICommand>()
        var isPoint = true

        try {
            // открываем поток для чтения
            val br = BufferedReader(
                InputStreamReader(
                    context.openFileInput(FILENAME)
                )
            )

            // читаем содержимое
            while (br.readLine().also { str = it } != null) {
                if (str != null) {
                    if (str!!.trim() == "POINTS") {
                        isPoint = true
                    } else if (str!!.trim() == "PROGRAM") {
                        isPoint = false
                    }

                    if (isPoint) {
                        val point = str!!.split("=")
                        if (point.size == 2) {
                            points[point[0]] = listToPosition(point[1].split(";"))
                        }
                    } else if (!isPoint) {
                        val command = getCommandFromString(str!!)
                        if (command != null) {
                            commands.add(command)
                        }
                    }
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Pair(points, commands)
    }

    private fun listToPosition(split: List<String>): Position {
        val returnPosition = Position()

        split.forEachIndexed { index, position ->
            returnPosition[Axes.values()[index]] = position.toDouble()
        }

        return returnPosition
    }

    private fun getCommandFromString(command: String): UICommand? {
        return when (command.split("~")[0]) {
            UIMoveToPoint.command -> {
                getUIMoveToPoint(command)
            }
            UIMoveByAxes.command -> {
                getUIMoveByAxes(command)
            }
            Gripper.command -> {
                getGripper(command)
            }
            UIMoveNearby.command -> {
                getUIMoveNearby(command)
            }
            UIWaitSignal.command -> {
                getUIWaitSignal(command)
            }
            else -> {
                null
            }
        }
    }

    private fun getUIWaitSignal(command: String): UIWaitSignal? {
        val commandDetails = command.split("~")
        return UIWaitSignal(signal = commandDetails[1].toInt())
    }

    private fun getGripper(command: String): Gripper {
        val commandDetails = command.split("~")
        return if (commandDetails[1] == UIOpenGripper.commandOpen) {
            UIOpenGripper()
        } else {
            UICloseGripper()
        }
    }

    private fun getUIMoveByAxes(command: String): UIMoveByAxes {
        val commandDetails = command.split("~")
        return UIMoveByAxes(
            axes = Axes.valueOf(commandDetails[1]),
            distance = commandDetails[2].toFloat()
        )
    }

    private fun getUIMoveToPoint(command: String): UIMoveToPoint {
        val commandDetails = command.split("~")
        return UIMoveToPoint(
            pointName = commandDetails[1],
            type = TypeOfMovement.valueOf(commandDetails[2])
        )
    }

    private fun getUIMoveNearby(command: String): UIMoveNearby {
        val commandDetails = command.split("~")
        return UIMoveNearby(
            pointName = commandDetails[1],
            axes = Axes.valueOf(commandDetails[2]),
            distance = commandDetails[3].toFloat(),
            angle = commandDetails[4].toFloat(),
        )
    }
}
