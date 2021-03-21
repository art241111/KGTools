package com.art241111.kgtools.ui.settingScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.art241111.kcontrolsystem.data.DELAY_SEND_SP
import com.art241111.kcontrolsystem.data.LONG_MOVE_SP
import com.art241111.kcontrolsystem.data.SHORT_MOVE_SP
import com.art241111.kgtools.R
import com.art241111.kgtools.ui.RobotVM
import com.art241111.kgtools.ui.views.TextHeader
import com.art241111.kgtools.ui.views.verticalGradientBackground
import com.art241111.saveandloadinformation.sharedPreferences.SharedPreferencesHelperForString

@Composable
internal fun SettingScreen(
    robot: RobotVM,
    sharedPreferences: SharedPreferencesHelperForString
) {
    Column(
        modifier = Modifier
            .verticalGradientBackground()
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextHeader(text = R.string.settings)

            Spacer(modifier = Modifier.height(15.dp))

            EditText(
                modifier = Modifier.fillMaxWidth(0.8f),
                value = robot.delaySending.toDouble(),
                labelText = stringResource(id = R.string.settings_delay_send),
                onValueChange = {
                    robot.delaySending = it.toLong()
                    sharedPreferences.save(DELAY_SEND_SP, it.toLong().toString())
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            EditText(
                modifier = Modifier.fillMaxWidth(0.8f),
                value = robot.defaultButtonDistanceLong,
                labelText = stringResource(id = R.string.settings_fast_move),
                onValueChange = {
                    robot.defaultButtonDistanceLong = it
                    sharedPreferences.save(LONG_MOVE_SP, it.toString())
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            EditText(
                modifier = Modifier.fillMaxWidth(0.8f),
                value = robot.defaultButtonDistanceShort,
                labelText = stringResource(id = R.string.settings_slow_move),
                onValueChange = {
                    robot.defaultButtonDistanceShort = it
                    sharedPreferences.save(SHORT_MOVE_SP, it.toString())
                }
            )
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
        ) {
            Chat()
        }
    }
}

@Composable
private fun EditText(
    modifier: Modifier = Modifier,
    value: Double,
    labelText: String,
    onValueChange: (Double) -> Unit
) {

    val text = remember { mutableStateOf(value) }

    TextField(
        modifier = modifier,
        value = text.value.toString(),
        onValueChange = {
            text.value = it.toDouble()

            onValueChange(it.toDouble())
        },
        label = {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = labelText,
                color = Color.White,
                fontFamily = Font(R.font.geometria).toFontFamily()
            )
        },
        textStyle = TextStyle(
            color = Color.White,
            fontFamily = Font(R.font.geometriabold).toFontFamily()
        ),
        shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10),
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                this.defaultKeyboardAction(ImeAction.Done)
            }
        ),
    )
}
