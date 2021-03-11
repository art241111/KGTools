package com.art241111.kgtools

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.art241111.kconnectscreen.data.Status
import com.art241111.kconnectscreen.ui.KConnectScreen
import com.art241111.kgtools.ui.theme.KGToolsTheme
import com.art241111.saveandloadinformation.sharedPreferences.SharedPreferencesHelperForString

private const val SHARED_PREFERENCES_NAME = "KGTools"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val helper = SharedPreferencesHelperForString(this, SHARED_PREFERENCES_NAME)
        setContent {
            KGToolsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(helper)
                }
            }
        }
    }
}

@Composable
fun Greeting(helper: SharedPreferencesHelperForString) {
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

// @Preview(showBackground = true)
// @Composable
// fun DefaultPreview() {
//     KGToolsTheme {
//         Greeting("Android")
//     }
// }
