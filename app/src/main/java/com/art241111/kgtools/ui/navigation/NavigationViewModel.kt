package com.art241111.kgtools.ui.navigation

import androidx.lifecycle.ViewModel
import java.util.Stack

interface AppState

class NavigationViewModel : ViewModel() {
    val stack = Stack<AppState>()
    val onBackList: MutableList<(AppState) -> Unit> = mutableListOf()

    fun back(): Boolean {
        return if (stack.empty()) {
            true
        } else {
            val newState = stack.pop()

            onBackList.forEach {
                it(newState)
            }

            false
        }
    }

    fun onBackButtonClick() = back()
}
