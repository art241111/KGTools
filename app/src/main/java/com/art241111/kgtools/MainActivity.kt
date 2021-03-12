package com.art241111.kgtools

import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art241111.kconnectscreen.data.Status
import com.art241111.kconnectscreen.ui.KConnectScreen
import com.art241111.kcontrolsystem.ControlVM
import com.art241111.kcontrolsystem.ControlView
import com.art241111.kcontrolsystem.ui.data.MoveInTime
import com.art241111.kcontrolsystem.ui.data.Position
import com.art241111.kcontrolsystem.ui.data.UIMoveByCoordinateKRobot
import com.art241111.kcontrolsystem.ui.utils.TiltControl
import com.art241111.kgtools.ui.theme.KGToolsTheme
import com.art241111.saveandloadinformation.sharedPreferences.SharedPreferencesHelperForString

private const val SHARED_PREFERENCES_NAME = "KGTools"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val helper = SharedPreferencesHelperForString(this, SHARED_PREFERENCES_NAME)

        val sensor = applicationContext.getSystemService(SENSOR_SERVICE) as SensorManager
        val tiltController = TiltControl(sensorManager = sensor)

        setContent {
            KGToolsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    // ConnectScreen(helper)
                    ControlView2(tiltController)
                }
            }
        }
    }
}

@Composable
fun ConnectScreen(helper: SharedPreferencesHelperForString) {
    val const_ip_name = "LOAD_IP"
    val context = LocalContext.current
    val ip = helper.load(const_ip_name, "192.168.31.63")
    KConnectScreen(
        onBack = { Toast.makeText(context, "Back", Toast.LENGTH_SHORT).show() },
        onConnect = { Toast.makeText(context, "Connect", Toast.LENGTH_SHORT).show() },
        connectStatus = mutableStateOf(Status.DISCONNECTED),
        onIpChange = { newIp ->
            helper.save(const_ip_name, newIp)
        },
        defaultIP = ip
    )
}

@Composable
fun ControlView2(tiltControl: TiltControl) {
    val controlVM = viewModel<ControlVM>()

    ControlView(
        coordinate = mutableStateOf(Position()),
        moveInTime = MoveInTime(move = { x, y, z, o, a, t -> }),
        moveByCoordinate = UIMoveByCoordinateKRobot(mutableStateOf(Position())) {},
        tiltControl = tiltControl,
        controlVM = controlVM
    )
}
