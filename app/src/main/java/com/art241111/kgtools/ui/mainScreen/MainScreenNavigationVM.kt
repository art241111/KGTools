package com.art241111.kgtools.ui.mainScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

enum class ShowHomeScreen {
    HOME_PROGRAM, HOME_POINTS
}

class MainScreenNavigationVM : ViewModel() {
    val homeState = mutableStateOf(ShowHomeScreen.HOME_PROGRAM)
}
