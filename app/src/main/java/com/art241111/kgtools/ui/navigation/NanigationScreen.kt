package com.art241111.kgtools.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art241111.kconnectscreen.ui.KConnectScreen
import com.art241111.kcontrolsystem.ui.utils.TiltControl
import com.art241111.kgtools.ui.RobotVM
import com.art241111.kgtools.ui.commands.NavigateProgramsScreen
import com.art241111.kgtools.ui.mainScreen.MainScreenNavigationVM
import com.art241111.kgtools.ui.mainScreen.ProgramAndPointsVM
import com.art241111.kgtools.ui.mainScreen.ProgramAndPointsWithBottomBar
import com.art241111.kgtools.ui.mainScreen.SurfaceWithProgramAndPoints
import com.art241111.kgtools.ui.points.AddPoint
import com.art241111.kgtools.ui.settingScreen.SettingScreen
import com.art241111.kgtools.ui.views.DefaultScreenBody
import com.art241111.saveandloadinformation.sharedPreferences.SharedPreferencesHelperForString

const val CONST_IP_NAME = "LOAD_IP"

/**
 * The screen where the main navigation of the application takes place.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
@Composable
fun MainNavigateScreen(
    navigate: NavigationViewModel,
    modifier: Modifier = Modifier,
    sharedPreferences: SharedPreferencesHelperForString,
    tiltControl: TiltControl,
) {
    val robot = viewModel<RobotVM>()
    robot.loadDefaultValue(sharedPreferences)

    val mainNavigationVM = viewModel<MainNavigationVM>()
    mainNavigationVM.setNavigation(navigate)

    val programAndPointsVM = viewModel<ProgramAndPointsVM>()
    with(programAndPointsVM) {
        context = LocalContext.current
        updateProgram = { mainNavigationVM.updateProgram() }
        updatePoint = { mainNavigationVM.moveAddPoint() }
        updateCommand = { mainNavigationVM.moveProgram() }
        loadPrograms()
    }

    val state = mainNavigationVM.getState()
    val homeState = viewModel<MainScreenNavigationVM>().homeState

    when (state.value) {
        ShowScreen.CLEAN -> {
            Box(modifier = modifier.fillMaxSize())
        }

        ShowScreen.SETTINGS -> {
            SettingScreen(
                robot = robot,
                sharedPreferences = sharedPreferences
            )
        }

        ShowScreen.HOME -> {
            ProgramAndPointsWithBottomBar(
                modifier = modifier,
                homeState = homeState,
                programAndPointsVM = programAndPointsVM,
                navigate = mainNavigationVM,
                robotVM = robot
            )
        }

        ShowScreen.PROGRAM -> {
            DefaultScreenBody(
                modifier = Modifier.fillMaxSize(),
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    SurfaceWithProgramAndPoints(
                        programAndPointsVM = programAndPointsVM,
                        homeState = homeState
                    )

                    NavigateProgramsScreen(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        programAndPointsVM = programAndPointsVM,
                        navigationViewModel = navigate,
                        closeProgramMenu = {
                            mainNavigationVM.moveHome()
                            programAndPointsVM.uiCommandUpgrade = null
                        },
                        editUICommand = programAndPointsVM.uiCommandUpgrade
                    )
                }
            }
        }

        ShowScreen.POINT -> {
            AddPoint(
                modifier = modifier,
                navigateBack = {
                    mainNavigationVM.moveHome()
                },
                pointVM = programAndPointsVM,
                robotVM = robot,
                tiltControl = tiltControl
            )
        }

        ShowScreen.CONNECT -> {
            val defaultIp = sharedPreferences.load(CONST_IP_NAME, "192.168.31.63")
            KConnectScreen(
                onBack = { mainNavigationVM.moveHome() },
                onConnect = { ip -> robot.robot.connect(ip) },
                connectStatus = robot.robot.connectStatus,
                onIpChange = { newIp ->
                    sharedPreferences.save(CONST_IP_NAME, newIp)
                },
                defaultIP = defaultIp
            )
        }
    }
}
