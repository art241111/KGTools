package com.art241111.kgtools.utils

import android.content.Context
import com.art241111.kgtools.data.uiCommands.UICommand
import com.art241111.saveandloadinformation.files.FilesHelper
import com.github.poluka.kControlLibrary.enity.position.Point

private const val FILENAME = "kGT"

/**
 * A class for saving commands and points to a file, as well as for uploading them.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
class UIProgramUtils {
    private val filesHelper = FilesHelper()
    fun saveProgram(
        context: Context,
        UICommands: List<UICommand>,
        points: Map<String, Point>
    ) {
        filesHelper.writeToFile(
            fileName = FILENAME,
            fileText = createFile(points, UICommands),
            context = context
        )
    }

    private fun createFile(
        points: Map<String, Point>,
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

    fun loadProgram(context: Context): Pair<MutableMap<String, Point>, List<UICommand>> {
        val points = mutableMapOf<String, Point>()
        val commands = mutableListOf<UICommand>()
        var isPoint = true

        filesHelper.readFromFile(
            fileName = FILENAME,
            context = context,
        ) { textLine ->
            if (textLine.trim() == "POINTS") {
                isPoint = true
            } else if (textLine.trim() == "PROGRAM") {
                isPoint = false
            }

            if (isPoint) {
                val point = textLine.split("=")
                if (point.size == 2) {
                    points[point[0]] = listToPosition(point[1].split(";"))
                }
            } else if (!isPoint) {
                val command = getCommandFromString(textLine)
                if (command != null) {
                    commands.add(command)
                }
            }
        }
        return Pair(points, commands)
    }
}
