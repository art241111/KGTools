package com.art241111.kgtools.ui.settingScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.art241111.kcontrolsystem.ui.theme.red500
import com.art241111.kcontrolsystem.ui.theme.red700
import com.art241111.kgtools.R

@Preview
@Composable
internal fun Chat(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.padding(vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .weight(1f)
        ) {
            Column() {
                RobotMessage(text = "Robot connect")
                UserMessage(text = "Ok")
                RobotMessage(text = "Robot connect")
                UserMessage(text = "Ok")
                RobotMessage(text = "Robot connect")
                UserMessage(text = "Ok")
                RobotMessage(text = "Robot connect")
                UserMessage(text = "Ok")
                RobotMessage(text = "Robot connect")
                UserMessage(text = "Ok")
                RobotMessage(text = "Robot connect")
                UserMessage(text = "Ok")
                RobotMessage(text = "Robot connect")
                UserMessage(text = "Ok")
                RobotMessage(text = "Robot connect")
                UserMessage(text = "Ok")
            }
        }

        SendMessage(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun SendMessage(
    modifier: Modifier = Modifier
) {
    val message = remember { mutableStateOf("") }
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = message.value,
                onValueChange = { message.value = it },
                placeholder = {
                    Text("Message")
                }
            )

            IconButton(
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 10.dp),
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_send_24),
                    contentDescription = "Send",
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
private fun RobotMessage(
    modifier: Modifier = Modifier,
    text: String
) {
    Message(
        modifier = modifier,
        text = text,
        messageAlignment = Alignment.CenterStart,
        color = red700,
    )
}

@Composable
private fun UserMessage(
    modifier: Modifier = Modifier,
    text: String
) {
    Message(
        modifier = modifier,
        text = text,
        messageAlignment = Alignment.CenterEnd,
        color = red500,
    )
}

@Composable
private fun Message(
    modifier: Modifier = Modifier,
    text: String,
    messageAlignment: Alignment,
    color: Color = MaterialTheme.colors.onSurface
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = messageAlignment,
    ) {
        Surface(
            shape = RoundedCornerShape(40),
            color = color,
        ) {
            Text(
                modifier = modifier.padding(10.dp),
                text = text,
                color = Color.White
            )
        }
    }
}
