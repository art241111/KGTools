package com.art241111.kgtools.data.uiCommands

import androidx.compose.runtime.Composable
import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.enity.position.Position

/**
 * Interface describing the command UI.
 */
interface UICommand {
    @Composable
    fun toComposableString(): String

    fun getCommand(points: MutableMap<String, Position>): Command?
    fun getImage(): Int
    fun getProgramName(): Int
}
