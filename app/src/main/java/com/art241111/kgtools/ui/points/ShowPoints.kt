package com.art241111.kgtools.ui.points

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.art241111.kgtools.R
import com.art241111.kgtools.ui.card.PointItemCard
import com.art241111.kgtools.ui.commands.PreviewRobot
import com.art241111.kgtools.ui.mainScreen.ProgramAndPointsVM

@Composable
internal fun ShowPoints(
    modifier: Modifier = Modifier,
    viewModel: ProgramAndPointsVM
) {
    val pointsName = viewModel.getPointsName()
    val points = viewModel.getPoints()

    if (pointsName.value != null) {
        if (pointsName.value!!.size != 0) {
            LazyColumn(modifier = modifier) {
                itemsIndexed(pointsName.value!!) { index, item ->
                    val pointCoordinate = points.value!![item]
                    if (pointCoordinate != null) {

                        PointItemCard(
                            index = index,
                            pointName = pointsName.value!![index],
                            pointCoordinate = pointCoordinate.toString(),
                            onDeleteClick = {
                                viewModel.deletePoint(index)
                            },
                            onCardClick = {
                                viewModel.editPoint(item)
                            }
                        )
                    }
                }
            }
        } else {
            PreviewRobot(textId = R.string.points_first_point)
        }
    }
}
