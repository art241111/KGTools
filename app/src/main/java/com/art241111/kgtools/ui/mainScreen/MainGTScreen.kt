package com.art241111.kgtools.ui.mainScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.art241111.kcontrolsystem.ui.theme.red500
import com.art241111.kcontrolsystem.ui.theme.red700
import com.art241111.kgtools.R
import com.art241111.kgtools.ui.RobotVM
import com.art241111.kgtools.ui.commands.ShowCommands
import com.art241111.kgtools.ui.views.DefaultScreenBody
import com.art241111.kgtools.ui.views.TabButton
import com.art241111.kgtools.ui.points.ShowPoints
import com.art241111.kgtools.ui.navigation.MainNavigationVM
import com.art241111.kgtools.utils.RunProgram
import kotlinx.coroutines.flow.asStateFlow

/**
 * Создается из [MainNavigateScreen]
 */

@Composable
internal fun ProgramAndPointsWithBottomBar(
    modifier: Modifier = Modifier,
    homeState: MutableState<ShowHomeScreen>,
    programAndPointsVM: ProgramAndPointsVM,
    robotVM: RobotVM,
    navigate: MainNavigationVM,
) {
    val fabShape = CircleShape

    Scaffold(
        modifier = modifier,

        content = { paddingValue ->
            DefaultScreenBody(
                modifier = Modifier
                    .padding(paddingValue)
                    .fillMaxSize(),
                settingsNavigate = { navigate.moveSettings() }
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    SurfaceWithProgramAndPoints(
                        programAndPointsVM = programAndPointsVM,
                        homeState = homeState,
                    )
                }
            }
        },

        bottomBar = {
            BottomAppBar(
                backgroundColor = Color.White,
                cutoutShape = fabShape,
                elevation = 10.dp
            ) {
                if (robotVM.robot.connect.value) {
                    IconButton(
                        onClick = {
                            robotVM.robot.disconnect()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(35.dp),
                            tint = Color.Green,
                            painter =
                            painterResource(id = R.drawable.ic_baseline_link_24),
                            contentDescription = null
                        )
                    }
                } else {

                    IconButton(
                        onClick = { navigate.moveConnect() }
                    ) {
                        Icon(
                            modifier = Modifier.size(35.dp),
                            tint = Color.Red,
                            painter =
                            painterResource(id = R.drawable.ic_baseline_link_off_48_red),
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.width(5.dp))

                val runProgram = RunProgram()
                val isRun = runProgram.isRun.asStateFlow()

                if (!isRun.value) {
                    IconButton(
                        onClick = {
                            runProgram.runProgram(
                                robot = robotVM.robot.robot,
                                UICommands = programAndPointsVM.getCommands().value!!,
                                points = programAndPointsVM.getPoints().value!!
                            )
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(35.dp),
                            tint = Color.Green,
                            painter =
                            painterResource(id = R.drawable.ic_baseline_start_48_green),
                            contentDescription = null
                        )
                    }
                } else {
                    IconButton(
                        onClick = {
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(35.dp),
                            tint = red700,
                            painter =
                            painterResource(id = R.drawable.ic_baseline_stop_24),
                            contentDescription = null
                        )
                    }
                }
            }
        },

        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = fabShape,
                onClick = {
                    if (homeState.value == ShowHomeScreen.HOME_PROGRAM) {
                        navigate.moveProgram()
                    } else {
                        navigate.moveAddPoint()
                    }
                },
                backgroundColor = red500
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter =
                    painterResource(id = R.drawable.ic_baseline_add_48),
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
internal fun SurfaceWithProgramAndPoints(
    modifier: Modifier = Modifier,
    programAndPointsVM: ProgramAndPointsVM,
    homeState: MutableState<ShowHomeScreen>,
) {
    val isProgramShow = homeState.value == ShowHomeScreen.HOME_PROGRAM

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            TabButton(
                isSelected = isProgramShow,
                text = stringResource(id = R.string.program),
                onClick = {
                    homeState.value = ShowHomeScreen.HOME_PROGRAM
                }
            )

            Spacer(modifier = Modifier.width(20.dp))

            TabButton(
                isSelected = !isProgramShow,
                text = stringResource(id = R.string.points),
                onClick = {
                    homeState.value = ShowHomeScreen.HOME_POINTS
                }
            )
        }

        if (isProgramShow) {
            ShowCommands(viewModel = programAndPointsVM)
        } else {
            ShowPoints(viewModel = programAndPointsVM)
        }
    }
}
