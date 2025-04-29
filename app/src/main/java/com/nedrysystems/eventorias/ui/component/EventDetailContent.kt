package com.nedrysystems.eventorias.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.ui.theme.GrayEventoriasBackground
import com.nedrysystems.eventorias.ui.uiModel.EventUiModel

@Composable
fun EventDetailContent(
    event: EventUiModel,
    innerPadding: PaddingValues = PaddingValues()
) {
    val context = LocalContext.current

    val eventImageDescription = stringResource(R.string.event_image_description)
    val profileImageDescription = stringResource(R.string.profile_image_description)
    val dateDescription = stringResource(R.string.event_date_description)
    val timeDescription = stringResource(R.string.event_time_description)
    val descriptionText = stringResource(R.string.event_description_description)
    val addressText = stringResource(R.string.event_address_description)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(GrayEventoriasBackground)
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(event.eventImage)
                .crossfade(true)
                .error(R.drawable.error)
                .build()
        )

        Image(
            painter = painter,
            contentDescription = eventImageDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(400.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                .semantics { contentDescription = eventImageDescription }
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(modifier = Modifier.padding(8.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.event),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = event.formattedDate,
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.semantics { contentDescription = "$dateDescription: ${event.formattedDate}" }
                    )
                }

                Row(modifier = Modifier.padding(8.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.schedule),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = event.formattedTime,
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.semantics { contentDescription = "$timeDescription: ${event.formattedTime}" }
                    )
                }
            }

            val painterProfile = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(event.profileImage)
                    .crossfade(true)
                    .error(R.drawable.error)
                    .build()
            )

            Image(
                painter = painterProfile,
                contentDescription = profileImageDescription,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(55.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape)
                    .semantics { contentDescription = profileImageDescription }
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = event.description,
            color = Color.White,
            modifier = Modifier.semantics {
                contentDescription = "$descriptionText: ${event.description}"
            }
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = event.address,
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .semantics {
                        contentDescription = "$addressText: ${event.address}"
                    }
            )

            event.coordinateGps?.let {
                StaticMap(
                    latitude = it.latitude,
                    longitude = it.longitude,
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .width(200.dp)
                        .semantics {
                            contentDescription = "null"
                        }
                )
            }
        }
    }
}
