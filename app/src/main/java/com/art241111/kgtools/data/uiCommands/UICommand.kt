package com.art241111.kgtools.data.uiCommands

import androidx.compose.runtime.Composable
import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.enity.position.Position

/**
 * Interface describing the command UI.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
interface UICommand {
    /**
     * Text to be entered on the screen.
     */
    @Composable
    fun toComposableString(): String

    /**
     * Returns the command that is associated with the given UI command.
     * @param points - Map of points. Needed for those teams that use positions.
     */
    fun getCommand(points: MutableMap<String, Position>): Command?

    /**
     * Returns the value of the image that should be displayed on the screen.
     */
    fun getImage(): Int

    /**
     * Returns the program name
     */
    fun getProgramName(): Int
}
