package com.art241111.kgtools.ui.commands

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art241111.kgtools.data.*
import com.art241111.kgtools.ui.commands.commandScreens.ShowHome
import com.art241111.kgtools.ui.commands.commandScreens.ShowMoveByAxes
import com.art241111.kgtools.ui.commands.commandScreens.ShowMoveNearby
import com.art241111.kgtools.ui.commands.commandScreens.ShowMoveToPoint
import com.art241111.kgtools.ui.commands.commandScreens.WaitSignal
import com.art241111.kgtools.ui.mainScreen.ProgramAndPointsVM
import com.art241111.kgtools.ui.navigation.NavigationViewModel

@Composable
internal fun NavigateProgramsScreen(
    modifier: Modifier = Modifier,
    programAndPointsVM: ProgramAndPointsVM,
    navigationViewModel: NavigationViewModel,
    editUICommand: UICommand?,
    closeProgramMenu: () -> Unit
) {
    val programNavigateVm = viewModel<ProgramNavigateVm>()
    programNavigateVm.setNavigation(navigationViewModel, editUICommand)

    val state = programNavigateVm.getState()

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(topStartPercent = 20, topEndPercent = 20),
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state.value) {
                StateProgram.MoveByAxis -> {
                    ShowMoveByAxes(
                        programAndPointsVM,
                        programNavigateVm,
                        editUICommand as UIMoveByAxes?,
                        closeProgramMenu = {
//                            programNavigateVm.moveHome()
                            closeProgramMenu()
                        }
                    )
                }
                StateProgram.MoveToPoint -> {
                    ShowMoveToPoint(
                        programAndPointsVM,
                        programNavigateVm,
                        editUICommand as UIMoveToPoint?,
                        closeProgramMenu
                    )
                }
                StateProgram.MoveNearby -> {
                    ShowMoveNearby(
                        programAndPointsVM,
                        programNavigateVm,
                        editUICommand as UIMoveNearby?,
                        closeProgramMenu
                    )
                }
                StateProgram.WaitSignal -> {
                    WaitSignal(
                        programAndPointsVM,
                        programNavigateVm,
                        editUICommand as UIWaitSignal?,
                        closeProgramMenu
                    )
                }
                StateProgram.Home -> {
                    ShowHome(programAndPointsVM, programNavigateVm, closeProgramMenu)
                }
            }
        }
    }
}
