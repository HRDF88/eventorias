package com.nedrysystems.eventorias.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.ui.theme.GraysEventoriasField
import com.nedrysystems.eventorias.ui.uiModel.EventUiModel

@Composable
fun EventCard(eventUi: EventUiModel, onClick: () -> Unit) {
    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = GraysEventoriasField),
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .wrapContentHeight()
            .wrapContentWidth()
            .clickable(onClick = onClick),


        ) {
        val profilePictureTextContentDescription =
            stringResource(R.string.profile_picture_description)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .background(GraysEventoriasField),


            ) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(eventUi.profileImage)
                    .crossfade(true)
                    .error(R.drawable.error)
                    .build()
            )
            Image(
                painter = painter,
                contentDescription = profilePictureTextContentDescription,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape)
                    .semantics { contentDescription = profilePictureTextContentDescription }


            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(GraysEventoriasField),


                ) {
                Text(
                    text = eventUi.title,
                    color = Color.White
                )
                Text(
                    text = eventUi.formattedDate,
                    color = Color.White
                )
            }

            Image(
                bitmap = eventUi.eventImage.asImageBitmap(),
                contentDescription = "Event Image",
                modifier = Modifier
                    .width(150.dp)
                    .height(100.dp)
                    .background(GraysEventoriasField),
                contentScale = ContentScale.Fit
            )
        }
    }
}