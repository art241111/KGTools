package com.art241111.kgtools

import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art241111.kcontrolsystem.ui.utils.TiltControl
import com.art241111.kgtools.ui.navigation.MainNavigateScreen
import com.art241111.kgtools.ui.navigation.NavigationViewModel
import com.art241111.kgtools.ui.theme.KGToolsTheme
import com.art241111.saveandloadinformation.sharedPreferences.SharedPreferencesHelperForString

private const val KG_TOOLS_SHARED_PREFERENCES_NAME = "KG_TOOLS"

class MainActivity : ComponentActivity() {
    private lateinit var navigationVM: NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sensor = applicationContext.getSystemService(SENSOR_SERVICE) as SensorManager
        val tiltController = TiltControl(sensorManager = sensor)

        setContent {
            navigationVM = viewModel()

            KGToolsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    // ConnectScreen(helper)
                    MainNavigateScreen(
                        navigate = navigationVM,
                        sharedPreferences = SharedPreferencesHelperForString(
                            this,
                            KG_TOOLS_SHARED_PREFERENCES_NAME
                        ),
                        tiltControl = tiltController
                    )
                }
            }
        }
    }

    override fun onBackPressed() {
        if (navigationVM.onBackButtonClick()) {
            super.onBackPressed()
        }
    }
}

// @Composable
// fun ConnectScreen(helper: SharedPreferencesHelperForString) {
//     val const_ip_name = "LOAD_IP"
//     val context = LocalContext.current
//     val ip = helper.load(const_ip_name, "192.168.31.63")
//     KConnectScreen(
//         onBack = { Toast.makeText(context, "Back", Toast.LENGTH_SHORT).show() },
//         onConnect = { Toast.makeText(context, "Connect", Toast.LENGTH_SHORT).show() },
//         connectStatus = mutableStateOf(Status.DISCONNECTED),
//         onIpChange = { newIp ->
//             helper.save(const_ip_name, newIp)
//         },
//         defaultIP = ip
//     )
// }
//
// @Composable
// fun ControlView2(tiltControl: TiltControl) {
//     val controlVM = viewModel<ControlVM>()
//
//     ControlView(
//         coordinate = mutableStateOf(PositionImp()),
//         moveInTime = MoveInTime(move = { x, y, z, o, a, t -> }),
//         moveByCoordinate = UIMoveByCoordinateKRobot(mutableStateOf(PositionImp())) {},
//         tiltControl = tiltControl,
//         controlVM = controlVM
//     )
// }
