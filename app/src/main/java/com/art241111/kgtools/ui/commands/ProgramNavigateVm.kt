package com.art241111.kgtools.ui.commands

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.art241111.kgtools.data.uiCommands.UICommand
import com.art241111.kgtools.data.uiCommands.UIMoveByAxes
import com.art241111.kgtools.data.uiCommands.UIMoveNearby
import com.art241111.kgtools.data.uiCommands.UIMoveToPoint
import com.art241111.kgtools.data.uiCommands.UIWaitSignal
import com.art241111.kgtools.ui.navigation.AppState
import com.art241111.kgtools.ui.navigation.NavigationViewModel

/**
 * Данный enum класс содержит дополнительные экраны, которые нам надо перерисовать.
 */
enum class StateProgram : AppState {
    Home, MoveToPoint, MoveByAxis, MoveNearby, WaitSignal
}

class ProgramNavigateVm : ViewModel() {
    private lateinit var navigation: NavigationViewModel
    fun setNavigation(
        value: NavigationViewModel,
        indexEditUICommand: UICommand?
    ) {
        state.value =
            when (indexEditUICommand) {
                is UIMoveByAxes -> StateProgram.MoveByAxis
                is UIMoveToPoint -> StateProgram.MoveToPoint
                is UIMoveNearby -> StateProgram.MoveNearby
                is UIWaitSignal -> StateProgram.WaitSignal
                else -> StateProgram.Home
            }

        value.onBackList.add { onBackButtonClick(it) }
        navigation = value
    }

    private val state = mutableStateOf(StateProgram.Home)
    fun getState(): State<StateProgram> = state

    fun moveToPoint() {
        navigation.stack.add(state.value)
        state.value = StateProgram.MoveToPoint
    }

    fun moveByAxis() {
        navigation.stack.add(state.value)
        state.value = StateProgram.MoveByAxis
    }

    fun moveNearby() {
        navigation.stack.add(state.value)
        state.value = StateProgram.MoveNearby
    }

    fun moveWaitSignal() {
        navigation.stack.add(state.value)
        state.value = StateProgram.WaitSignal
    }

    fun back() {
        navigation.back()
    }

    private fun onBackButtonClick(appState: AppState) {
        if (appState is StateProgram) {
            state.value = appState
        }
    }
}
