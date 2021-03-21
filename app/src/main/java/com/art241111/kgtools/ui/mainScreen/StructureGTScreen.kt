package com.art241111.kgtools.ui.mainScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.art241111.kcontrolsystem.ui.theme.red500
import com.art241111.kgtools.R
import com.art241111.kgtools.data.robot.RobotVM
import com.art241111.kgtools.ui.navigation.MainNavigationVM
import com.art241111.kgtools.ui.views.DefaultScreenBody

/**
 * The structure of the main screen with a bottom bar that has a floating action bar.
 *
 * Created from [MainNavigateScreen]
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
            BottomBar(
                robotVM = robotVM,
                navigate = navigate,
                programAndPointsVM = programAndPointsVM
            )
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
