package com.art241111.kgtools.ui.mainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.art241111.kgtools.R
import com.art241111.kgtools.ui.commands.ShowCommands
import com.art241111.kgtools.ui.points.ShowPoints
import com.art241111.kgtools.ui.views.TabButton

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

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
