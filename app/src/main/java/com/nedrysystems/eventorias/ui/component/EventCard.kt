package com.nedrysystems.eventorias.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.ui.uiModel.EventUiModel

@Composable
fun EventCard(eventUi: EventUiModel,) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .wrapContentHeight()
            .wrapContentWidth(),



        ) {
        val profilePictureTextContentDescription = stringResource(R.string.profile_picture_description)
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),


            ) {
            Image(
                bitmap = eventUi.profileImage.asImageBitmap(),
                contentDescription = profilePictureTextContentDescription,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape)
                    .semantics { contentDescription = profilePictureTextContentDescription }

            )
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),


                ) {
                Text(text = eventUi.title,
                    color = Color.White
                )
                Text(text = eventUi.formattedDate)
            }


        }

        Image(
            bitmap = eventUi.eventImage.asImageBitmap(),
            contentDescription = "Event Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )
    }
}