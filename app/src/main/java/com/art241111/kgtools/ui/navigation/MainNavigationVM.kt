package com.art241111.kgtools.ui.navigation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

enum class ShowScreen : AppState {
    HOME, PROGRAM, POINT, CONNECT, CLEAN, SETTINGS
}

class MainNavigationVM : ViewModel() {
    private lateinit var navigation: NavigationViewModel

    fun setNavigation(value: NavigationViewModel) {
        value.stack.add(ShowScreen.HOME)
        value.onBackList.add { onBackButtonClick(it) }
        navigation = value
    }

    private val state = mutableStateOf(ShowScreen.HOME)
    fun getState(): State<ShowScreen> = state

    fun moveHome() {
        navigation.stack.clear()
        state.value = ShowScreen.HOME
    }

    fun moveAddPoint() {
        navigation.stack.add(state.value)
        state.value = ShowScreen.POINT
    }

    fun moveConnect() {
        navigation.stack.add(state.value)
        state.value = ShowScreen.CONNECT
    }

    fun moveProgram() {
        navigation.stack.add(state.value)
        state.value = ShowScreen.PROGRAM
    }

    fun updateProgram() {
        navigation.stack.add(state.value)
        state.value = ShowScreen.CLEAN
        state.value = navigation.stack.pop() as ShowScreen
    }

    fun moveSettings() {
        navigation.stack.add(state.value)
        state.value = ShowScreen.SETTINGS
    }

    private fun onBackButtonClick(appState: AppState) {
        if (appState is ShowScreen) {
            state.value = appState
        }
    }
}
