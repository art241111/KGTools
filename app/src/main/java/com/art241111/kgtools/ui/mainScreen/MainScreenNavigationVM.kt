package com.art241111.kgtools.ui.mainScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 * This enum class contains a list of screens with commands and points that we need to redraw.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
enum class ShowHomeScreen {
    HOME_PROGRAM, HOME_POINTS
}

/**
 * This class allows you to store the navigation state between the
 * screens for displaying points and the screen for displaying a list of commands.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
class MainScreenNavigationVM : ViewModel() {
    val homeState = mutableStateOf(ShowHomeScreen.HOME_PROGRAM)
}
