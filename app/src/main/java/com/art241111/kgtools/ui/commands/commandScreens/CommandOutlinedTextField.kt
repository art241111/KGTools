package com.art241111.kgtools.ui.commands.commandScreens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * Optimizing the Outlined Text Field for teams.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
@Composable
fun CommandOutlinedTextField(
    modifier: Modifier = Modifier,
    onDone: () -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    label: Int,
) {
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(id = label)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                defaultKeyboardAction(ImeAction.Done)

                onDone()
            }
        )
    )
}
