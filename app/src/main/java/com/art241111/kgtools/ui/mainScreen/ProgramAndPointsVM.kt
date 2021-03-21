package com.art241111.kgtools.ui.mainScreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.art241111.kgtools.data.uiCommands.UICommand
import com.art241111.kgtools.utils.UIProgramUtils
import com.github.poluka.kControlLibrary.enity.position.Position

class ProgramAndPointsVM : ViewModel() {
    lateinit var updatePoint: () -> Unit
    lateinit var updateCommand: () -> Unit

    lateinit var updateProgram: () -> Unit
    var pointNameUpgrade: Int? = null
    var uiCommandUpgrade: UICommand? = null
    private var uiCommandIndexUpgrade: Int? = null

    private val pointsName: LiveData<MutableList<String>> = MutableLiveData(arrayListOf())
    fun getPointsName(): LiveData<MutableList<String>> = pointsName

    private val points: LiveData<MutableMap<String, Position>> = MutableLiveData(mutableMapOf())
    fun getPoints(): LiveData<MutableMap<String, Position>> = points

    private val uiCommands: MutableList<UICommand> = arrayListOf()
    private val commandsLiveData = MutableLiveData<List<UICommand>>()
    fun getCommands(): LiveData<List<UICommand>> = commandsLiveData

    private val utils = UIProgramUtils()
    lateinit var context: Context

    fun loadPrograms() {
        val readFromFile = utils.loadProgram(context)
        points.value?.putAll(readFromFile.first)

        pointsName.value?.clear()
        readFromFile.first.keys.forEach {
            pointsName.value?.add(it)
        }

        uiCommands.clear()
        uiCommands.addAll(readFromFile.second)
        commandsLiveData.value = uiCommands
    }

    private fun savePrograms() {
        utils.saveProgram(
            context = context,
            points = points.value!!,
            UICommands = uiCommands
        )
    }

    fun addPoint(
        name: String,
        coordinate: Position
    ): Boolean {
        return if (!pointsName.value?.contains(name)!!) {
            pointsName.value?.add(name)
            points.value?.set(name, coordinate)
            savePrograms()
            updateProgram()
            true
        } else {
            false
        }
    }

    fun replacePoint(
        index: Int,
        newName: String,
        newCoordinate: Position
    ) {
        points.value?.remove(pointsName.value?.get(index))
        pointsName.value?.set(index, newName)
        points.value?.set(newName, newCoordinate)

        savePrograms()
        updateProgram()
    }

    fun deletePoint(
        index: Int
    ) {
        points.value?.remove(pointsName.value?.get(index))
        pointsName.value?.removeAt(index)
        savePrograms()
        updateProgram()
    }

    fun addCommand(
        UICommand: UICommand
    ) {
        if (uiCommandUpgrade != null && uiCommandIndexUpgrade != null) {
            uiCommands[uiCommandIndexUpgrade!!] = UICommand
        } else {
            uiCommands.add(UICommand)
        }

        commandsLiveData.value = uiCommands
        savePrograms()
        updateProgram()
    }

    fun deleteProgram(
        index: Int
    ) {
        uiCommands.removeAt(index)
        commandsLiveData.value = uiCommands
        savePrograms()
        updateProgram()
    }

    fun editPoint(name: String) {
        pointNameUpgrade = pointsName.value?.indexOf(name)
        updatePoint()
        savePrograms()
    }

    fun editCommand(UICommand: UICommand, index: Int) {
        uiCommandUpgrade = UICommand
        uiCommandIndexUpgrade = index

        updateCommand()
        savePrograms()
    }
}
