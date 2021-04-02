package com.art241111.kgtools.ui.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.art241111.kgtools.R
import com.art241111.kgtools.ui.views.StartBoldEndNormalText

/**
 * Card for displaying points.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
@Composable
internal fun PointItemCard(
    modifier: Modifier = Modifier,
    index: Int,
    pointName: String,
    pointCoordinate: String,
    onDeleteClick: () -> Unit,
    onCardClick: () -> Unit,
) {
    CardWithCloseButtonAndIndex(
        modifier = modifier.clickable { onCardClick() },
        index = index,
        onDeleteButton = onDeleteClick,
        content = {
            // Text block
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                StartBoldEndNormalText(
                    modifier = Modifier.padding(bottom = 5.dp),
                    startText = "${stringResource(id = R.string.point_name)}: ",
                    endText = pointName
                )

                StartBoldEndNormalText(
                    startText = "${stringResource(id = R.string.point_coordinate)}: ",
                    endText = pointCoordinate
                )
            }
        }
    )
}
